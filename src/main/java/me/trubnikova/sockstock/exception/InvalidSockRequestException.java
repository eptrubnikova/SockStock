package me.trubnikova.sockstock.exception;

public class InvalidSockRequestException extends RuntimeException {
    public InvalidSockRequestException(String message) {
        super(message);
    }
}
