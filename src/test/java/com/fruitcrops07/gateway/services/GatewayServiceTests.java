package com.fruitcrops07.gateway.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.fruitcrops07.gateway.exceptions.GatewaySerialNumberException;
import com.fruitcrops07.gateway.exceptions.InvalidIPV4AddressException;
import com.fruitcrops07.gateway.exceptions.PeriphiralSizeExceedException;
import com.fruitcrops07.gateway.models.Device;
import com.fruitcrops07.gateway.models.DeviceStatus;
import com.fruitcrops07.gateway.models.Gateway;
import com.fruitcrops07.gateway.repositories.DeviceRepository;
import com.fruitcrops07.gateway.repositories.GatewayRepository;

@SpringBootTest
public class GatewayServiceTests {

    @Autowired
    private GatewayService gatewayService;

    @MockBean
    private GatewayRepository gatewayRepository;

    @MockBean
    private DeviceRepository deviceRepository;

    @BeforeEach
    public void setup() {
        final String gatewaySerialNumber = "TEST1";
        List<Device> devices = Arrays.asList(new Device(1, gatewaySerialNumber, "sampsung", new Date(), DeviceStatus.online),
                                             new Device(2, gatewaySerialNumber, "apple", new Date(), DeviceStatus.online),
                                             new Device(3, gatewaySerialNumber, "huawei", new Date(), DeviceStatus.offline));
        Gateway gateway = new Gateway(gatewaySerialNumber, "gateway1", "192.168.1.1", devices);

        Mockito.when(gatewayRepository.findById(gatewaySerialNumber)).thenReturn(Optional.of(gateway));
    }

    @Test
    public void givenASerialNumber_checkIfDeviceExists() {
        final String serialNumber = "TEST1";
        Optional<Gateway> result = gatewayService.findBySerialNumber(serialNumber);
        assertEquals(true, result.isPresent());
    }

    @Test
    public void givenAnEmptySerialNumberOnGatewayCreation_throwGatewaySerialNumberException() {
        final String emptySerialNumber = "";

        Gateway gateway = new Gateway(emptySerialNumber, "EMPTY SERIAL GATEWAY", "192.178.1.1", Collections.EMPTY_LIST);

        assertThrows(GatewaySerialNumberException.class, () -> {
            gatewayService.create(gateway);
        });
    }

    @Test
    public void givenAnInvalidIPV4Address_throwInvalidIPV4AddressException() {
        final String invalidIPV4 = "invalid_ip";

        Gateway gateway = new Gateway("TESTING_SERIAL", "EMPTY SERIAL GATEWAY", invalidIPV4, Collections.EMPTY_LIST);

        assertThrows(InvalidIPV4AddressException.class, () -> {
            gatewayService.create(gateway);
        });
    }

    @Test
    public void givenMoreThan10Peripherals_throwPeriphiralSizeExceedException() {
        List<Device> moreThan10Peripherals = Arrays.asList(new Device(),
                                                 new Device(),
                                                 new Device(),
                                                 new Device(),
                                                 new Device(),
                                                 new Device(),
                                                 new Device(),
                                                 new Device(),
                                                 new Device(),
                                                 new Device(),
                                                 new Device());

        Gateway gateway = new Gateway("TESTING_SERIAL", "EMPTY SERIAL GATEWAY", "192.168.1.1", moreThan10Peripherals);

        assertThrows(PeriphiralSizeExceedException.class, () -> {
            gatewayService.create(gateway);
        });
    }
}
