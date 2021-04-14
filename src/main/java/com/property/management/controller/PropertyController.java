package com.property.management.controller;

import com.property.management.dto.ApprovalResponse;
import com.property.management.dto.CreatePropertyRequest;
import com.property.management.dto.PropertyResponse;
import com.property.management.dto.UpdatePropertyRequest;
import com.property.management.service.PropertyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/properties")
@Log4j2
@RequiredArgsConstructor
public class PropertyController {

    private final PropertyService propertyService;

    @PostMapping
    public ResponseEntity<PropertyResponse> create(@Valid @RequestBody CreatePropertyRequest createPropertyRequest) {
        log.info("Rest API to create property {}", createPropertyRequest);
        return ResponseEntity.ok(propertyService.create(createPropertyRequest));
    }

    @PutMapping
    public ResponseEntity<PropertyResponse> update(@Valid @RequestBody UpdatePropertyRequest updatePropertyRequest) {
        log.info("Rest API to update property {}", updatePropertyRequest);
        return ResponseEntity.ok(propertyService.update(updatePropertyRequest));
    }

    @GetMapping
    public ResponseEntity<List<PropertyResponse>> search(@RequestParam String propertyName) {
        log.info("Rest API to search property by name: {}", propertyName);
        return ResponseEntity.ok(propertyService.search(propertyName));
    }

    @PutMapping("/{propertyId}/approval")
    public ResponseEntity<ApprovalResponse> approve(@RequestHeader("api-key") String apiKey, @PathVariable Long propertyId) {
        log.info("Rest API to approve property by api-key: {} and propertyId: {}", apiKey, propertyId);
        return ResponseEntity.ok(propertyService.approve(apiKey, propertyId));
    }
}
