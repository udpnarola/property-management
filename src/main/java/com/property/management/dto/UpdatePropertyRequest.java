package com.property.management.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

import static com.property.management.constant.Constants.PROPERTY_ID_NULL;

@Getter
@Setter
public class UpdatePropertyRequest extends PropertyDto {

    @NotNull(message = PROPERTY_ID_NULL)
    private Long id;
}
