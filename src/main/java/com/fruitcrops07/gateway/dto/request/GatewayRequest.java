package com.fruitcrops07.gateway.dto.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@JsonPropertyOrder({
    "serial_number",
    "name",
    "address",
    "periphirals"
})
@Data
public class GatewayRequest {

    @JsonProperty("serial_number")
    private String serialNumber;

    @JsonProperty("name")
    private String name;

    @JsonProperty("address")
    private String address;
    
    @JsonProperty("periphirals")
    private List<DeviceRequest> periphirals;
}
