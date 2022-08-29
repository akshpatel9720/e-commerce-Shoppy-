package com.user.enums;

public enum PromocodeEnum {

    FLAT("flat"),
    PERCENTAGE("percentage");

    private String type;

    PromocodeEnum(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "PromocodeEnum{" +
                "type='" + type + '\'' +
                '}';
    }
}
