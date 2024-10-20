package edu.sliit.exception;

public class InvalidDataException extends RuntimeException {
    public InvalidDataException(String message, String exMessage) {
        super(message);
    }
}