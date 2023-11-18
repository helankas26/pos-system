package com.devstack.pos.bo.custom;

import com.devstack.pos.bo.SuperBo;
import com.devstack.pos.dto.UserRoleDto;

import java.util.List;

public interface UserRoleBo extends SuperBo {
    List<UserRoleDto> loadAllUserRoles();
    boolean saveUserRole(UserRoleDto userRoleDto);
}
