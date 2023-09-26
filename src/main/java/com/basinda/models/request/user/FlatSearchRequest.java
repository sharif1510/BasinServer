package com.basinda.models.request.user;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FlatSearchRequest {
    private Long divisionId;
    private Long districtId;
    private Long upozillaId;
    private Long pourosovaId;
}