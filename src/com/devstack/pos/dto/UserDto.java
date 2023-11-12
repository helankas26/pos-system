package com.devstack.pos.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private  Long propertyId;
    private String username;
    private String password;
    private String displayName;
    private boolean activeState;
    private UserRoleDto userRoleDto;
}
