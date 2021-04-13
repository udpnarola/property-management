package com.property.management.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PropertyResponse extends PropertyDto {

    private Long id;
    private Boolean isApproved;
}
