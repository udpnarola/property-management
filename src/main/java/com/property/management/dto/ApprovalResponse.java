package com.property.management.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ApprovalResponse {

    private PropertyDto propertyDetail;

    private UserResponse approvedBy;

    public ApprovalResponse(PropertyDto propertyDetail, UserResponse approvedBy){
        this.propertyDetail = propertyDetail;
        this.approvedBy = approvedBy;
    }
}
