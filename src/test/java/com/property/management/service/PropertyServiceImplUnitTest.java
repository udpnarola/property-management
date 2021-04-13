package com.property.management.service;

import com.property.management.dto.CreatePropertyRequest;
import com.property.management.dto.PropertyResponse;
import com.property.management.entity.Property;
import com.property.management.mapper.PropertyMapper;
import com.property.management.repository.PropertyRepository;
import com.property.management.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class PropertyServiceImplUnitTest {

    @InjectMocks
    private PropertyServiceImpl propertyService;
    @Mock
    private PropertyRepository propertyRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PropertyMapper propertyMapper;

    private static CreatePropertyRequest createPropertyRequest;

    @BeforeAll
    static void initCreatePropertyRequest() {
        CreatePropertyRequest property = new CreatePropertyRequest();
        property.setName("Forest House");
        property.setType(1);
        property.setAddress("50, down town");
        property.setBedroom(2);
        property.setBathroom(1);
        property.setRent(70.88);
        property.setIsFurnished(false);
        createPropertyRequest = property;
    }

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void whenValidData_thenPropertyShouldBeCreate() {
        Property property = new Property();
        property.setId(1L);
        PropertyResponse propertyResponse = new PropertyResponse();
        propertyResponse.setId(1L);

        Mockito.when(propertyMapper.toProperty(any(CreatePropertyRequest.class))).thenReturn(property);
        Mockito.when(propertyRepository.save(property)).thenReturn(property);
        Mockito.when(propertyMapper.toPropertyResponse(property)).thenReturn(propertyResponse);

        PropertyResponse savedProperty = propertyService.create(createPropertyRequest);
        assertEquals(property.getId(), savedProperty.getId());
    }
}
