package com.sikorski;

public enum Role {
    USER,
    ADMIN,
    SUPERADMIN;

    @Override
    public String toString() {
        return name();
    }
}
