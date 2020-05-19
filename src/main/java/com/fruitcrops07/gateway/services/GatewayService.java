package com.fruitcrops07.gateway.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fruitcrops07.gateway.exceptions.GatewaySerialNumberException;
import com.fruitcrops07.gateway.exceptions.InvalidIPV4AddressException;
import com.fruitcrops07.gateway.exceptions.PeriphiralSizeExceedException;
import com.fruitcrops07.gateway.models.Device;
import com.fruitcrops07.gateway.models.Gateway;
import com.fruitcrops07.gateway.repositories.DeviceRepository;
import com.fruitcrops07.gateway.repositories.GatewayRepository;
import com.fruitcrops07.gateway.util.IPV4Validator;

@Service
@Transactional
public class GatewayService {

    @Autowired
    private GatewayRepository gatewayRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private IPV4Validator ipv4Validator;

    public Gateway create(Gateway gateway) throws Exception {

        if (gateway.getSerialNumber() == null)
            throw new GatewaySerialNumberException();

        if (gateway.getSerialNumber().isEmpty())
            throw new GatewaySerialNumberException();

        Optional<Gateway> existingRecord = gatewayRepository.findById(gateway.getSerialNumber());
        if (existingRecord.isPresent())
            throw new GatewaySerialNumberException(gateway.getSerialNumber());

        if (gateway.getPeriphirals().size() > 10)
            throw new PeriphiralSizeExceedException(gateway.getPeriphirals().size());

        if (!ipv4Validator.isValid(gateway.getAddress()))
            throw new InvalidIPV4AddressException(gateway.getAddress());

        String gatewaySerialNumber = gateway.getSerialNumber();
        Date createdDate = new Date();
        gateway.getPeriphirals().forEach(device -> {
            device.setGatewaySerialNumber(gatewaySerialNumber);
            device.setCreatedDate(createdDate);
        });
        Gateway result = gatewayRepository.save(gateway);
        List<Device> devices = deviceRepository.saveAll(gateway.getPeriphirals());
        result.setPeriphirals(devices);
        return result;
    }

    public List<Gateway> findAll() {
        return gatewayRepository.findAll();
    }

    public Optional<Gateway> findBySerialNumber(String serialNumber) {
        return gatewayRepository.findById(serialNumber);
    }

    public Device addDevice(String gatewaySerialNumber, Device device) {
        device.setGatewaySerialNumber(gatewaySerialNumber);
        device.setCreatedDate(new Date());
        return deviceRepository.save(device);
    }

    public void removeDevice(Integer deviceId) {
        deviceRepository.deleteById(deviceId);
    }
}
