package com.devstack.pos.dao;

import java.util.List;

public interface CrudDao<T, ID> extends SuperDao {
    boolean create(T t);
    T find(ID id);
    boolean remove(ID id);
    boolean modify(T t);
    List<T> loadAll();
}
