package com.fruitcrops07.gateway.exceptions;

public class PeriphiralSizeExceedException extends Exception{

    public PeriphiralSizeExceedException(Integer numberOfPeriphiralsAsParams) {
        super("Peripherals exceeded. Number of periphirals: " + numberOfPeriphiralsAsParams + " Limit: 10");
    }
}
