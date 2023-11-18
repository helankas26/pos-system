package com.devstack.pos.bo.custom;

import com.devstack.pos.bo.SuperBo;
import com.devstack.pos.dto.AccessPointDto;
import com.devstack.pos.dto.UserRoleDto;

import java.util.List;

public interface AccessPointBo extends SuperBo {
    List<AccessPointDto> loadAlAccessPoints();
    boolean createAccessPoint(AccessPointDto dto);
    List<AccessPointDto> loadAlAccessPoints(String searchText);
    boolean dropAccessPoint(Long accessPointId);
    void updateAccessPoint(AccessPointDto accessPointDto);
}
