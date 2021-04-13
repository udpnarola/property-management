package com.property.management.service;

import com.property.management.dto.*;

import java.util.List;

public interface PropertyService {

    PropertyResponse create(CreatePropertyRequest createPropertyRequest);

    PropertyResponse update(UpdatePropertyRequest updatePropertyRequest);

    List<PropertyResponse> search(String name);

    ApprovalResponse approve(String apiKey, Long propertyId);
}
