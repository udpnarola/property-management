package com.property.management.mapper;

import com.property.management.dto.ApprovalResponse;
import com.property.management.dto.PropertyDto;
import com.property.management.dto.PropertyResponse;
import com.property.management.dto.UpdatePropertyRequest;
import com.property.management.entity.Property;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", implementationName = "PropertyMapperImp", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PropertyMapper {

    PropertyResponse toPropertyResponse(Property property);

    Property toProperty(PropertyDto propertyDto);

    Property toProperty(UpdatePropertyRequest updatePropertyRequest);

    @Mapping(target = "approvedBy", source = "user")
    ApprovalResponse toApprovalResponse(Property property);
}
