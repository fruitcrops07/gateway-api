package com.fruitcrops07.gateway.exceptions;

public class InvalidIPV4AddressException extends Exception {

    public InvalidIPV4AddressException(String IPV4Address) {
        super("Invalid IPV4 Address: " + IPV4Address);
    }
}
