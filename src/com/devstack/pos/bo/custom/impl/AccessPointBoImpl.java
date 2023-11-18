package com.devstack.pos.bo.custom.impl;

import com.devstack.pos.bo.custom.AccessPointBo;
import com.devstack.pos.dao.DaoFactory;
import com.devstack.pos.dao.custom.AccessPointDao;
import com.devstack.pos.dto.AccessPointDto;
import com.devstack.pos.entity.AccessPoint;

import java.util.ArrayList;
import java.util.List;

public class AccessPointBoImpl implements AccessPointBo {

    private AccessPointDao accessPointDao = DaoFactory.getDao(DaoFactory.DaoType.ACCESS_POINT);

    @Override
    public List<AccessPointDto> loadAlAccessPoints() {
        List<AccessPointDto> accessPointDtos = new ArrayList<>();
        for (AccessPoint accessPoint : accessPointDao.loadAll()) {
            accessPointDtos.add(
                    new AccessPointDto(accessPoint.getPropertyId(), accessPoint.getPointName())
            );
        }
        return accessPointDtos;
    }

    @Override
    public boolean createAccessPoint(AccessPointDto dto) {
        return accessPointDao.create(new AccessPoint(dto.getPropertyId(), dto.getPointName(), null));
    }

    @Override
    public List<AccessPointDto> loadAlAccessPoints(String searchText) {
        List<AccessPointDto> accessPointDtos = new ArrayList<>();

        for (AccessPoint accessPoint : accessPointDao.loadAllAccessPoints(searchText)) {
            accessPointDtos.add(
                    new AccessPointDto(
                            accessPoint.getPropertyId(),
                            accessPoint.getPointName())
            );
        }

        return accessPointDtos;
    }

    @Override
    public boolean dropAccessPoint(Long accessPointId) {
        return accessPointDao.remove(accessPointId);
    }

    @Override
    public void updateAccessPoint(AccessPointDto accessPointDto) {
        accessPointDao.modify(
                new AccessPoint(accessPointDto.getPropertyId(), accessPointDto.getPointName(), null)
        );
    }
}
