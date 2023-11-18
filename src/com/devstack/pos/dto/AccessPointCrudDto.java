package com.devstack.pos.dto;

import com.devstack.pos.enums.Crud;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AccessPointCrudDto {
    private Long propertyId;
    private Crud crud;
    private AccessPointDto accessPointDto;
}
