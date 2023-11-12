package com.devstack.pos.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserRoleDto {
    private  Long propertyId;
    private String roleName;
    private String roleDescription;
}
