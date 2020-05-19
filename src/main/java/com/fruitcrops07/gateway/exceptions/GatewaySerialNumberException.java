package com.fruitcrops07.gateway.exceptions;

public class GatewaySerialNumberException extends Exception {

    public GatewaySerialNumberException(String serialNumber) {
        super("Gateway with serial number: " + serialNumber + " already exists.");
    }
    
    public GatewaySerialNumberException() {
        super("Gateway serial number is required.");
    }
}
