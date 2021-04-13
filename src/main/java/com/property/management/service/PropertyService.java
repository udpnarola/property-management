package com.property.management.service;

import com.property.management.dto.ApprovalResponse;
import com.property.management.dto.CreatePropertyRequest;
import com.property.management.dto.PropertyDto;
import com.property.management.dto.UpdatePropertyRequest;

import java.util.List;

public interface PropertyService {

    PropertyDto create(CreatePropertyRequest createPropertyRequest);

    PropertyDto update(UpdatePropertyRequest updatePropertyRequest);

    List<PropertyDto> search(String name);

    ApprovalResponse approve(String apiKey, Long propertyId);
}
