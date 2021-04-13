package com.property.management.mapper;

import com.property.management.dto.CreatePropertyRequest;
import com.property.management.dto.PropertyDto;
import com.property.management.dto.UserResponse;
import com.property.management.entity.Property;
import com.property.management.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PropertyMapper {

    PropertyDto toPropertyDto(Property property);

    @Mapping(target = "id", ignore = true)
    Property toProperty(CreatePropertyRequest createPropertyRequest);

    Property toProperty(PropertyDto propertyDto);

    UserResponse toUserResponse(User user);
}
