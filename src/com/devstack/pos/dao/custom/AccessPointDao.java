package com.devstack.pos.dao.custom;

import com.devstack.pos.dao.CrudDao;
import com.devstack.pos.entity.AccessPoint;

import java.util.List;

public interface AccessPointDao extends CrudDao<AccessPoint,Long> {
    List<AccessPoint> loadAllAccessPoints(String searchText);
}
