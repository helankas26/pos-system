package com.devstack.pos.bo.custom;

import com.devstack.pos.bo.SuperBo;
import com.devstack.pos.dto.AccessPointCrudDto;
import com.devstack.pos.dto.AccessPointDto;

import java.util.List;

public interface AccessPointCrudBo extends SuperBo {
    boolean setPrivileges(List<AccessPointCrudDto> privilegeDtos);
    boolean dropPrivileges(List<AccessPointCrudDto> privilegeDtos);
    List<AccessPointCrudDto> findAllPrivilegesByAccessPoint(Long id);
    List<AccessPointCrudDto> loadAllAccessPointCruds(String searchText);
    boolean dropAllAccessPointCruds(AccessPointDto accessPointDto);
}
