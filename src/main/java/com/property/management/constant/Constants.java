package com.property.management.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {

    public static final String API_KEY = "api-key";
    public static final Integer DEFAULT_ADDRESS_LENGTH = 5;

    //error
    public static final String ERR_UNAUTHORIZED = "You are unauthorized";
    public static final String ERR_USER_NOT_FOUND = "User not found for the given api-key";
    public static final String ERR_PROPERTY_NOT_FOUND = "Property not found for the given id";
    public static final String ERR_PROPERTY_TYPE_NOT_FOUND = "Property type not found";
    public static final String ERR_PROPERTY_ALREADY_APPROVED = "This property has been already approved";
    public static final String ERR_ADDRESS_LENGTH_NOT_ENOUGH = "Property address length should be at least: "+DEFAULT_ADDRESS_LENGTH;

    //validation
    public static final String PROPERTY_ID_NULL = "Property id can't be null";
    public static final String PROPERTY_TYPE_NULL = "Property type can't be null";
    public static final String PROPERTY_NAME_BLANK = "Property name can't be empty or null";
    public static final String PROPERTY_ADDRESS_BLANK = "Property address can't be empty or null";
    public static final String PROPERTY_BEDROOM_NULL = "Property bedroom can't be null";
    public static final String PROPERTY_BATHROOM_NULL = "Property bathroom can't be null";
    public static final String PROPERTY_RENT_NULL = "Property rent can't be null";
    public static final String PROPERTY_FURNISHED_NULL = "Enter property furnished or not";
}
