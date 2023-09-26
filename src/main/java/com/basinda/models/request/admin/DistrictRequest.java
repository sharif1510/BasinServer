package com.basinda.models.request.admin;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DistrictRequest {
    private String name;
    private Long divisionId;
}