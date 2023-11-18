package com.devstack.pos.bo.custom;

import com.devstack.pos.bo.SuperBo;
import com.devstack.pos.dto.UserDto;

import java.util.List;

public interface UserBo extends SuperBo {
    void initializeSystem();
    List<UserDto> loadAllUsers(String searchText);
    void createNewSystemUser(Long userRoleId, String displayName, String email);
    void updateSystemUser(Long userRoleId, Long userId, String displayName, String email);
    boolean dropUser(Long userId);
}
