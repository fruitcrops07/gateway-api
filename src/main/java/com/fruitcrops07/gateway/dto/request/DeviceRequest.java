package com.fruitcrops07.gateway.dto.request;

import com.fruitcrops07.gateway.models.DeviceStatus;

import lombok.Data;

@Data
public class DeviceRequest {

    private String vendor;

    private DeviceStatus status;
}
