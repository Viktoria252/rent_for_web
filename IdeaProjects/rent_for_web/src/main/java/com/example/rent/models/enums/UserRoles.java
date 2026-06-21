package com.example.rent.models.enums;

public enum UserRoles {
    USER(1),
    ADMIN(2),
    MODERATOR(3);
    private final int value;

    UserRoles(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
