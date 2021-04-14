package com.property.management.service;

import com.property.management.constant.PropertyType;
import com.property.management.dto.*;
import com.property.management.entity.Property;
import com.property.management.entity.User;
import com.property.management.mapper.PropertyMapper;
import com.property.management.repository.PropertyRepository;
import com.property.management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

import static com.property.management.constant.Constants.*;

@Service
@Log4j2
@RequiredArgsConstructor
public class PropertyServiceImpl implements PropertyService {

    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;
    private final PropertyMapper propertyMapper;

    @Override
    @Transactional
    public PropertyResponse create(CreatePropertyRequest createPropertyRequest) {
        log.info("Service method to create property: {}", createPropertyRequest);
        validatePropertyRequest(createPropertyRequest);
        Property createdProperty = propertyRepository
                .save(propertyMapper.toProperty(createPropertyRequest));
        log.info("Property successfully created {}", createdProperty);
        return propertyMapper.toPropertyResponse(createdProperty);
    }

    @Override
    @Transactional
    public PropertyResponse update(UpdatePropertyRequest updatePropertyRequest) {
        log.info("Service method to update property: {}", updatePropertyRequest);
        validatePropertyRequest(updatePropertyRequest);

        Property property = propertyRepository
                .findById(updatePropertyRequest.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ERR_PROPERTY_NOT_FOUND));

        Property propertyToUpdate = propertyMapper.toProperty(updatePropertyRequest);

        if (property.getIsApproved()) {
            propertyToUpdate.setIsApproved(true);
            propertyToUpdate.setUser(property.getUser());
        }

        Property updatedProperty = propertyRepository.save(propertyToUpdate);
        log.info("Property successfully updated: {}", updatedProperty);
        return propertyMapper.toPropertyResponse(updatedProperty);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PropertyResponse> search(String name) {
        log.info("Service method to search property: {}", name);
        return propertyRepository
                .findByNameContainsAndIsApprovedTrue(name)
                .parallelStream()
                .map(propertyMapper::toPropertyResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ApprovalResponse approve(String apiKey, Long propertyId) {
        Property property = propertyRepository
                .findById(propertyId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ERR_PROPERTY_NOT_FOUND));

        if (Boolean.TRUE.equals(property.getIsApproved()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ERR_PROPERTY_ALREADY_APPROVED);

        property.setIsApproved(true);

        User user = userRepository
                .findByApiKey(apiKey)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ERR_USER_NOT_FOUND));
        user.addProperty(property);

        return propertyMapper.toApprovalResponse(property);
    }

    private void validatePropertyRequest(PropertyDto propertyDto) {
        if (!PropertyType.exists(propertyDto.getType()))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ERR_PROPERTY_TYPE_NOT_FOUND);
        if (propertyDto.getAddress().length() < DEFAULT_ADDRESS_LENGTH)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ERR_ADDRESS_LENGTH_NOT_ENOUGH);
    }

}
