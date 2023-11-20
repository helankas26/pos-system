package com.devstack.pos.dao.custom;

import com.devstack.pos.dao.CrudDao;
import com.devstack.pos.entity.AccessPoint;
import com.devstack.pos.entity.AccessPointCrud;

import java.util.List;

public interface AccessPointCrudDao extends CrudDao<AccessPointCrud, Long> {

    boolean setPrivileges(List<AccessPointCrud> privileges);
    boolean dropPrivileges(List<AccessPointCrud> privileges);
    List<AccessPointCrud> findAllPrivilegesByAccessPoint(Long id);
    List<AccessPointCrud> loadAllAccessPointCruds(String searchText);
    boolean removeAllAccessPointCruds(AccessPoint accessPoint);
}
