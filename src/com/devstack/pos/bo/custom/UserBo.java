package com.devstack.pos.bo.custom;

import com.devstack.pos.dto.UserDto;

import java.util.List;

public interface UserBo {
    void initializeSystem();
    List<UserDto> loadAllUsers(String searchText);
    void createNewSystemUser(Long userRoleId, String displayName, String email);
}
