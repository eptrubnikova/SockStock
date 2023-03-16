package me.trubnikova.sockstock.exception;

public class InSufficientSockQuantity extends RuntimeException {

    public InSufficientSockQuantity(String message) {
        super(message);
    }
}
