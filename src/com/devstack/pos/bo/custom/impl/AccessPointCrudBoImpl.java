package com.devstack.pos.bo.custom.impl;

import com.devstack.pos.bo.custom.AccessPointCrudBo;
import com.devstack.pos.dao.DaoFactory;
import com.devstack.pos.dao.custom.AccessPointCrudDao;
import com.devstack.pos.dto.AccessPointCrudDto;
import com.devstack.pos.dto.AccessPointDto;
import com.devstack.pos.entity.AccessPoint;
import com.devstack.pos.entity.AccessPointCrud;

import java.util.ArrayList;
import java.util.List;

public class AccessPointCrudBoImpl implements AccessPointCrudBo {

    private AccessPointCrudDao accessPointCrudDao = DaoFactory.getDao(DaoFactory.DaoType.ACCESS_POINT_CRUD);

    @Override
    public boolean setPrivileges(List<AccessPointCrudDto> privilegeDtos) {
        List<AccessPointCrud> privileges = new ArrayList<>();
        for (AccessPointCrudDto privilegeDto : privilegeDtos) {
            privileges.add(
                    new AccessPointCrud(
                            privilegeDto.getPropertyId(),
                            privilegeDto.getCrud(),
                            new AccessPoint(
                                    privilegeDto.getAccessPointDto().getPropertyId(),
                                    privilegeDto.getAccessPointDto().getPointName(),
                                    null
                            )
                    )
            );
        }

        return accessPointCrudDao.setPrivileges(privileges);
    }

    @Override
    public boolean dropPrivileges(List<AccessPointCrudDto> privilegeDtos) {
        List<AccessPointCrud> privileges = new ArrayList<>();
        for (AccessPointCrudDto privilegeDto : privilegeDtos) {
            privileges.add(
                    new AccessPointCrud(
                            privilegeDto.getPropertyId(),
                            privilegeDto.getCrud(),
                            new AccessPoint(
                                    privilegeDto.getAccessPointDto().getPropertyId(),
                                    privilegeDto.getAccessPointDto().getPointName(),
                                    null
                            )
                    )
            );
        }

        return accessPointCrudDao.dropPrivileges(privileges);
    }

    @Override
    public List<AccessPointCrudDto> findAllPrivilegesByAccessPoint(Long id) {
        List<AccessPointCrudDto> privilegeDto = new ArrayList<>();

        for (AccessPointCrud accessPointCrud : accessPointCrudDao.findAllPrivilegesByAccessPoint(id)) {
            privilegeDto.add(
                    new AccessPointCrudDto(
                            accessPointCrud.getPropertyId(),
                            accessPointCrud.getCrud(),
                            new AccessPointDto(
                                    accessPointCrud.getAccessPoint().getPropertyId(),
                                    accessPointCrud.getAccessPoint().getPointName()
                            )
                    )
            );
        }

        return privilegeDto;
    }

    @Override
    public List<AccessPointCrudDto> loadAllAccessPointCruds(String searchText) {
        List<AccessPointCrudDto> accessPointCrudDtos = new ArrayList<>();

        for (AccessPointCrud accessPointCrud : accessPointCrudDao.loadAllAccessPointCruds(searchText)) {
            accessPointCrudDtos.add(
                    new AccessPointCrudDto(
                            accessPointCrud.getPropertyId(),
                            accessPointCrud.getCrud(),
                            new AccessPointDto(
                                    accessPointCrud.getAccessPoint().getPropertyId(),
                                    accessPointCrud.getAccessPoint().getPointName()
                            )
                    )
            );
        }

        return accessPointCrudDtos;
    }

    @Override
    public boolean dropAllAccessPointCruds(AccessPointDto accessPointDto) {
        return accessPointCrudDao.removeAllAccessPointCruds(
                new AccessPoint(accessPointDto.getPropertyId(), accessPointDto.getPointName(), null)
        );
    }
}
