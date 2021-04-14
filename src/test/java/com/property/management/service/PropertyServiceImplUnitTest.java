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
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static com.property.management.constant.Constants.ERR_ADDRESS_LENGTH_NOT_ENOUGH;
import static com.property.management.constant.Constants.ERR_PROPERTY_TYPE_NOT_FOUND;
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
    private static Property property;
    private static PropertyResponse propertyResponse;

    @BeforeAll
    static void init() {
        CreatePropertyRequest createPropertyReq = new CreatePropertyRequest();
        createPropertyReq.setName("Forest House");
        createPropertyReq.setType(1);
        createPropertyReq.setAddress("50, down town");
        createPropertyReq.setBedroom(2);
        createPropertyReq.setBathroom(1);
        createPropertyReq.setRent(70.88);
        createPropertyReq.setIsFurnished(false);
        createPropertyRequest = createPropertyReq;

        Property propertyEntity = new Property();
        propertyEntity.setId(1L);
        property = propertyEntity;

        PropertyResponse propertyRes = new PropertyResponse();
        propertyRes.setId(1L);
        propertyResponse = propertyRes;
    }

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void when_valid_data_then_property_should_be_created() {
        Mockito.when(propertyMapper.toProperty(any(CreatePropertyRequest.class))).thenReturn(property);
        Mockito.when(propertyRepository.save(property)).thenReturn(property);
        Mockito.when(propertyMapper.toPropertyResponse(property)).thenReturn(propertyResponse);

        PropertyResponse savedProperty = propertyService.create(createPropertyRequest);
        assertEquals(property.getId(), savedProperty.getId());
    }

    @Test
    public void on_create_property_when_invalid_property_type_throw_notfound_exception() {
        CreatePropertyRequest createPropertyReq = createPropertyRequest;
        createPropertyReq.setType(4);
        try {
            propertyService.create(createPropertyReq);
        } catch (ResponseStatusException e) {
            assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
            assertEquals(ERR_PROPERTY_TYPE_NOT_FOUND, e.getReason());
        }
    }

    @Test
    public void on_create_property_when_address_length_is_less_throw_badRequest_exception() {
        CreatePropertyRequest createPropertyReq = createPropertyRequest;
        createPropertyReq.setAddress("add");
        try {
            propertyService.create(createPropertyReq);
        } catch (ResponseStatusException e) {
            assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
            assertEquals(ERR_ADDRESS_LENGTH_NOT_ENOUGH, e.getReason());
        }
    }


}
