package com.devstack.pos.dao.custom;

import com.devstack.pos.dao.CrudDao;
import com.devstack.pos.entity.User;
import com.devstack.pos.util.ResponseData;

import java.util.List;

public interface UserDao extends CrudDao<User, Long> {
    ResponseData login(String username, String password);
    List<User> loadAllUsers(String searchText);
    void createNewSystemUser(Long userRoleId, String displayName, String email);
    void updateSystemUser(Long userRoleId, Long userId, String displayName, String email);
}
