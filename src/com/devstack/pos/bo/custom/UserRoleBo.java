package com.devstack.pos.bo.custom;

import com.devstack.pos.dto.UserRoleDto;

import java.util.List;

public interface UserRoleBo {
    List<UserRoleDto> loadAllUserRoles();
}
