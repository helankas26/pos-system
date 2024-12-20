package com.devstack.pos.bo.custom.impl;

import com.devstack.pos.bo.custom.UserRoleBo;
import com.devstack.pos.dao.DaoFactory;
import com.devstack.pos.dao.custom.UserRoleDao;
import com.devstack.pos.dto.UserRoleDto;
import com.devstack.pos.entity.UserRole;

import java.util.ArrayList;
import java.util.List;

public class UserRoleBoImpl implements UserRoleBo {
    private UserRoleDao userRoleDao = DaoFactory.getDao(DaoFactory.DaoType.USER_ROLE);

    @Override
    public List<UserRoleDto> loadAllUserRoles() {
        List<UserRoleDto> userRoleDtos = new ArrayList<>();
        for (UserRole roles : userRoleDao.loadAll()) {
            userRoleDtos.add(
                    new UserRoleDto(roles.getPropertyId(), roles.getRoleName(), roles.getRoleDescription())
            );
        }
        return userRoleDtos;
    }

    @Override
    public List<UserRoleDto> loadAllUserRoles(String searchText) {
        List<UserRoleDto> userRoleDtos = new ArrayList<>();

        for (UserRole roles : userRoleDao.loadAllUserRoles(searchText)) {
            userRoleDtos.add(
                    new UserRoleDto(
                            roles.getPropertyId(),
                            roles.getRoleName(),
                            roles.getRoleDescription())
            );
        }

        return userRoleDtos;
    }

    @Override
    public boolean saveUserRole(UserRoleDto userRoleDto) {
        return userRoleDao.create(
                new UserRole(userRoleDto.getPropertyId(), userRoleDto.getRoleName(), userRoleDto.getRoleDescription(), null)
        );
    }

    @Override
    public boolean dropUserRole(Long userRoleId) {
        return userRoleDao.remove(userRoleId);
    }

    @Override
    public void updateUserRole(UserRoleDto userRoleDto) {
        userRoleDao.modify(
                new UserRole(userRoleDto.getPropertyId(), userRoleDto.getRoleName(), userRoleDto.getRoleDescription(), null)
        );
    }
}
