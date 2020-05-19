package com.fruitcrops07.gateway.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fruitcrops07.gateway.dto.request.DeviceRequest;
import com.fruitcrops07.gateway.dto.request.GatewayRequest;
import com.fruitcrops07.gateway.dto.response.ResponseMessage;
import com.fruitcrops07.gateway.models.Device;
import com.fruitcrops07.gateway.models.Gateway;
import com.fruitcrops07.gateway.services.GatewayService;

@RestController
@RequestMapping("/gateways")
public class GatewayController {

    @Autowired
    private GatewayService gatewayService;

    @GetMapping
    public List<Gateway> getAll() {
        return gatewayService.findAll();
    }

    @PostMapping
    public ResponseEntity<ResponseMessage<Gateway>> create(@RequestBody GatewayRequest gatewayRequest) {
        HttpStatus status = null;
        ResponseMessage<Gateway> message = null;
        try {
            status = HttpStatus.OK;
            Gateway gateway = new Gateway();
            List<Device> devices = new ArrayList<Device>();
            for (DeviceRequest deviceRequest : gatewayRequest.getPeriphirals()) {
                Device device = new Device();
                device.setStatus(deviceRequest.getStatus());
                device.setVendor(deviceRequest.getVendor());
                devices.add(device);
            }
            BeanUtils.copyProperties(gatewayRequest, gateway);
            gateway.setPeriphirals(devices);
            Gateway result = gatewayService.create(gateway);
            message = new ResponseMessage<Gateway>(result, status, "Gateway successfully created.");
            return new ResponseEntity<ResponseMessage<Gateway>>(message, status);
        } catch (Exception e) {
            status = HttpStatus.BAD_REQUEST;
            message = new ResponseMessage<Gateway>(null, status, e.getMessage());
            return new ResponseEntity<ResponseMessage<Gateway>>(message, status);
        }
    }

    @GetMapping("/{serial_number}")
    public ResponseEntity<ResponseMessage<Gateway>> getBySerialNumber(@PathVariable("serial_number") String serialNumber) {

        HttpStatus status = null;
        ResponseMessage<Gateway> responseMessage = null;
        Optional<Gateway> result = gatewayService.findBySerialNumber(serialNumber);
        if (result.isPresent()) {
            status = HttpStatus.OK;
            responseMessage = new ResponseMessage<Gateway>(result.get(), status, "Success");
            return new ResponseEntity<ResponseMessage<Gateway>>(responseMessage, status);
        }
        status = HttpStatus.NOT_FOUND;
        responseMessage = new ResponseMessage<Gateway>(null, status, "Gateway Not Found.");
        return new ResponseEntity<ResponseMessage<Gateway>>(responseMessage, status);
    }

    @PostMapping("/{serial_number}")
    public ResponseEntity<ResponseMessage<Void>> addDevice(@PathVariable("serial_number") String serialNumber,
                                                           @RequestBody DeviceRequest deviceRequest) {

        HttpStatus status = null;
        ResponseMessage<Void> responseMessage = null;
        Optional<Gateway> result = gatewayService.findBySerialNumber(serialNumber);
        if (!result.isPresent()) {
            status = HttpStatus.NOT_FOUND;
            responseMessage = new ResponseMessage<Void>(null, status, "Gateway Not Found.");
            return new ResponseEntity<ResponseMessage<Void>>(responseMessage, status);
        }

        if (result.get().getPeriphirals().size() == 10) {
            status = HttpStatus.BAD_REQUEST;
            responseMessage = new ResponseMessage<Void>(null,
                                                        status,
                                                        "Gateway: " + serialNumber + " has 10 periphirals already. Adding another device failed.");
            return new ResponseEntity<ResponseMessage<Void>>(responseMessage, status);
        }
        Device device = new Device();
        BeanUtils.copyProperties(deviceRequest, device);
        device = gatewayService.addDevice(serialNumber, device);
        status = HttpStatus.OK;
        responseMessage = new ResponseMessage<Void>(null,
                                                    status,
                                                    "Device with vendor: " + device.getVendor() + " successfull added to Gateway: " + serialNumber);
        return new ResponseEntity<ResponseMessage<Void>>(responseMessage, status);

    }

    @DeleteMapping("/{serial_number}/device/{device_id}")
    public ResponseEntity<ResponseMessage<Void>> removeDevice(@PathVariable("serial_number") String serialNumber,
                                                              @PathVariable("device_id") Integer deviceId) {
        HttpStatus status = null;
        ResponseMessage<Void> responseMessage = null;
        Optional<Gateway> result = gatewayService.findBySerialNumber(serialNumber);
        if (!result.isPresent()) {
            status = HttpStatus.NOT_FOUND;
            responseMessage = new ResponseMessage<Void>(null, status, "Gateway Not Found.");
            return new ResponseEntity<ResponseMessage<Void>>(responseMessage, status);
        }

        Long deviceCount = result.get().getPeriphirals().stream().filter(device -> device.getId() == deviceId).count();
        if (deviceCount == 0) {
            status = HttpStatus.NOT_FOUND;
            responseMessage = new ResponseMessage<Void>(null, status, "No device found with ID: " + deviceId + " on Gateway: " + serialNumber);
            return new ResponseEntity<ResponseMessage<Void>>(responseMessage, status);
        }

        gatewayService.removeDevice(deviceId);
        status = HttpStatus.OK;
        responseMessage = new ResponseMessage<Void>(null,
                                                    status,
                                                    "Device with ID:" + deviceId + " on Gateway: " + serialNumber + " was successfully removed.");
        return new ResponseEntity<ResponseMessage<Void>>(responseMessage, status);
    }
}
