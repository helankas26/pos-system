package com.devstack.pos.bo.custom;

import com.devstack.pos.bo.SuperBo;
import com.devstack.pos.dto.UserRoleDto;

import java.util.List;

public interface UserRoleBo extends SuperBo {
    List<UserRoleDto> loadAllUserRoles();
    List<UserRoleDto> loadAllUserRoles(String searchText);
    boolean saveUserRole(UserRoleDto userRoleDto);
    boolean dropUserRole(Long userRoleId);
    void updateUserRole(UserRoleDto userRoleDto);
}
