package com.property.management.constant;

import lombok.Getter;

@Getter
public enum PropertyType {

    HIGH_RISE(1),
    LANDED(2),
    ROOM(3);

    private Integer id;

    PropertyType(Integer id) {
        this.id = id;
    }

    public static Boolean exists(Integer id) {
        for (PropertyType propertyType : PropertyType.values()) {
            if (propertyType.getId().compareTo(id) == 0)
                return true;
        }
        return false;
    }

}
