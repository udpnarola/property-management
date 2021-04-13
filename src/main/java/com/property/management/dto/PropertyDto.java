package com.property.management.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static com.property.management.constant.Constants.*;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PropertyDto {

    @NotNull(message = PROPERTY_TYPE_NULL)
    private Integer type;

    @NotBlank(message = PROPERTY_NAME_BLANK)
    private String name;

    @NotBlank(message = PROPERTY_ADDRESS_BLANK)
    private String address;

    @NotNull(message = PROPERTY_BEDROOM_NULL)
    private Integer bedroom;

    @NotNull(message = PROPERTY_BATHROOM_NULL)
    private Integer bathroom;

    @NotNull(message = PROPERTY_RENT_NULL)
    private Double rent;

    @NotNull(message = PROPERTY_FURNISHED_NULL)
    private Boolean isFurnished;
}
