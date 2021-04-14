package com.property.management.service;

import com.property.management.dto.ApprovalResponse;
import com.property.management.dto.CreatePropertyRequest;
import com.property.management.dto.PropertyResponse;
import com.property.management.dto.UpdatePropertyRequest;
import com.property.management.entity.Property;
import com.property.management.entity.User;
import com.property.management.mapper.PropertyMapper;
import com.property.management.repository.PropertyRepository;
import com.property.management.repository.UserRepository;
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

import java.util.Collections;
import java.util.List;

import static com.property.management.constant.Constants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;

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

    private CreatePropertyRequest createPropertyRequest;
    private UpdatePropertyRequest updatePropertyRequest;
    private ApprovalResponse approvalResponse;
    private PropertyResponse propertyResponse;
    private Property property;
    private User user;

    @BeforeEach
    public void init() {
        //CreatePropertyRequest
        CreatePropertyRequest createPropertyRequest = new CreatePropertyRequest();
        createPropertyRequest.setName("Forest House");
        createPropertyRequest.setType(1);
        createPropertyRequest.setAddress("50, down town");
        createPropertyRequest.setBedroom(2);
        createPropertyRequest.setBathroom(1);
        createPropertyRequest.setRent(70.88);
        createPropertyRequest.setIsFurnished(false);
        this.createPropertyRequest = createPropertyRequest;

        //Property
        Property property = new Property();
        property.setId(1L);
        this.property = property;

        //PropertyResponse
        PropertyResponse propertyResponse = new PropertyResponse();
        propertyResponse.setId(1L);
        this.propertyResponse = propertyResponse;

        //UpdatePropertyRequest
        UpdatePropertyRequest updatePropertyRequest = new UpdatePropertyRequest();
        updatePropertyRequest.setId(1L);
        updatePropertyRequest.setName("Paradise House");
        updatePropertyRequest.setType(1);
        updatePropertyRequest.setAddress("50, down town");
        updatePropertyRequest.setBedroom(2);
        updatePropertyRequest.setBathroom(1);
        updatePropertyRequest.setRent(70.88);
        updatePropertyRequest.setIsFurnished(false);
        this.updatePropertyRequest = updatePropertyRequest;

        ApprovalResponse approvalResponse = new ApprovalResponse();
        approvalResponse.setIsApproved(true);
        this.approvalResponse = approvalResponse;

        //User
        User user = new User();
        user.setId(1L);
        user.setApiKey("123456");
        user.setFirstName("John");
        user.setLastName("Doe");
        this.user = user;
    }

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void when_valid_data_then_property_should_be_created() {
        Mockito.when(propertyMapper.toProperty(createPropertyRequest)).thenReturn(property);
        Mockito.when(propertyRepository.save(property)).thenReturn(property);
        Mockito.when(propertyMapper.toPropertyResponse(property)).thenReturn(propertyResponse);

        PropertyResponse savedProperty = propertyService.create(createPropertyRequest);
        assertEquals(property.getId(), savedProperty.getId());
    }

    @Test
    public void on_create_property_when_invalid_property_type_throw_notfound_exception() {
        createPropertyRequest.setType(4);
        try {
            propertyService.create(createPropertyRequest);
        } catch (ResponseStatusException e) {
            assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
            assertEquals(ERR_PROPERTY_TYPE_NOT_FOUND, e.getReason());
        }
    }

    @Test
    public void on_create_property_when_address_length_is_less_throw_badRequest_exception() {
        createPropertyRequest.setAddress("add");
        try {
            propertyService.create(createPropertyRequest);
        } catch (ResponseStatusException e) {
            assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
            assertEquals(ERR_ADDRESS_LENGTH_NOT_ENOUGH, e.getReason());
        }
    }

    @Test
    public void when_valid_data_then_property_should_be_updated() {
        property.setIsApproved(true);
        propertyResponse.setAddress(updatePropertyRequest.getAddress());
        Mockito.when(propertyMapper.toProperty(updatePropertyRequest)).thenReturn(property);
        Mockito.when(propertyRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(property));
        Mockito.when(propertyRepository.save(property)).thenReturn(property);
        Mockito.when(propertyMapper.toPropertyResponse(property)).thenReturn(propertyResponse);

        PropertyResponse updatedProperty = propertyService.update(updatePropertyRequest);
        assertEquals(property.getId(), updatedProperty.getId());
        assertEquals(propertyResponse.getAddress(), updatedProperty.getAddress());
    }

    @Test
    public void on_update_property_when_invalid_property_type_throw_notfound_exception() {
        updatePropertyRequest.setType(4);
        try {
            propertyService.update(updatePropertyRequest);
        } catch (ResponseStatusException e) {
            assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
            assertEquals(ERR_PROPERTY_TYPE_NOT_FOUND, e.getReason());
        }
    }

    @Test
    public void on_update_property_when_address_length_is_less_throw_badRequest_exception() {
        updatePropertyRequest.setAddress("add");
        try {
            propertyService.update(updatePropertyRequest);
        } catch (ResponseStatusException e) {
            assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
            assertEquals(ERR_ADDRESS_LENGTH_NOT_ENOUGH, e.getReason());
        }
    }

    @Test
    public void on_search_if_property_available_then_return_list_of_property() {
        property.setName("Forest House");
        Mockito.when(propertyRepository.findByNameContains(anyString())).thenReturn(Collections.singletonList(property));
        Mockito.when(propertyMapper.toPropertyResponse(property)).thenReturn(propertyResponse);

        List<PropertyResponse> searchedProperty = propertyService.search("Forest");
        assertEquals(searchedProperty.size(), 1);
        assertEquals(searchedProperty.get(0).getAddress(), property.getAddress());
    }

    @Test
    public void on_search_if_property_not_available_then_return_empty_list() {
        Mockito.when(propertyRepository.findByNameContains(anyString())).thenReturn(Collections.emptyList());

        List<PropertyResponse> searchedProperty = propertyService.search("Forest");
        assertTrue(searchedProperty.isEmpty());
    }

    @Test
    public void when_valid_data_then_property_should_be_approved() {
        property.setIsApproved(false);
        Mockito.when(propertyRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(property));
        Mockito.when(userRepository.findByApiKey(anyString())).thenReturn(java.util.Optional.ofNullable(user));
        Mockito.when(propertyMapper.toApprovalResponse(property)).thenReturn(approvalResponse);

        ApprovalResponse approvalResponse = propertyService.approve("12345",1L);
        assertTrue(approvalResponse.getIsApproved());
    }

    @Test
    public void when_property_is_already_approved_then_throw_badRequest_exception() {
        property.setIsApproved(true);
        Mockito.when(propertyRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(property));

        try {
            propertyService.approve("12345", 1L);
        }catch (ResponseStatusException e){
            assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
            assertEquals(ERR_PROPERTY_ALREADY_APPROVED, e.getReason());
        }
    }

}
