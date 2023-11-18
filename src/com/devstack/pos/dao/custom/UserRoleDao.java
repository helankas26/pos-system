package com.devstack.pos.dao.custom;

import com.devstack.pos.dao.CrudDao;
import com.devstack.pos.entity.UserRole;

import java.util.List;

public interface UserRoleDao extends CrudDao<UserRole, Long> {
    boolean isExists();
    List<UserRole> loadAllUserRoles(String searchText);
}
