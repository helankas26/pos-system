package com.devstack.pos.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AccessPointDto {
    private Long propertyId;
    private String pointName;
    private List<AccessPointCrudDto> accessPointCrudDtos;
}
