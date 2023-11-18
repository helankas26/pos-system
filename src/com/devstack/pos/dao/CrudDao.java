package com.devstack.pos.dao;

import com.devstack.pos.entity.SuperEntity;

import java.util.List;

public interface CrudDao<T extends SuperEntity, ID> extends SuperDao {
    boolean create(T t);
    T find(ID id);
    boolean remove(ID id);
    boolean modify(T t);
    List<T> loadAll();
}
