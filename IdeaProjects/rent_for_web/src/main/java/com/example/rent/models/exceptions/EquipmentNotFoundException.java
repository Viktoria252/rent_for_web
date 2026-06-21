package com.example.rent.models.exceptions;

public class EquipmentNotFoundException extends RuntimeException {

    public EquipmentNotFoundException(String message) {
        super(message);
    }

    public EquipmentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
