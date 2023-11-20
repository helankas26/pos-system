package com.devstack.pos.dao.custom.impl;

import com.devstack.pos.dao.custom.AccessPointCrudDao;
import com.devstack.pos.entity.AccessPoint;
import com.devstack.pos.entity.AccessPointCrud;
import com.devstack.pos.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class AccessPointCrudDaoImpl implements AccessPointCrudDao {
    @Override
    public boolean create(AccessPointCrud accessPointCrud) {
        return false;
    }

    @Override
    public AccessPointCrud find(Long id) {
        return null;
    }

    @Override
    public boolean remove(Long id) {
        return false;
    }

    @Override
    public boolean modify(AccessPointCrud accessPointCrud) {
        return false;
    }

    @Override
    public List<AccessPointCrud> loadAll() {
        return null;
    }

    @Override
    public boolean setPrivileges(List<AccessPointCrud> privileges) {
        try (Session session = HibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();

            for (AccessPointCrud privilege : privileges) {
                session.save(privilege);
            }

            transaction.commit();
        }

        return true;
    }

    @Override
    public boolean dropPrivileges(List<AccessPointCrud> privileges) {
        try (Session session = HibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();

            for (AccessPointCrud privilege : privileges) {
                Query<AccessPointCrud> query = session.createQuery("FROM AccessPointCrud a WHERE a.propertyId=:id", AccessPointCrud.class);
                query.setParameter("id", privilege.getPropertyId());
                AccessPointCrud accessPointCrud = query.uniqueResult();

                if (null != accessPointCrud) {
                    session.remove(accessPointCrud);
                } else {
                    throw new RuntimeException("Access Point Crud not found!");
                }
            }

            transaction.commit();
        }

        return true;
    }

    @Override
    public List<AccessPointCrud> findAllPrivilegesByAccessPoint(Long id) {
        try (Session session = HibernateUtil.getSession()) {
            Query<AccessPoint> query = session.createQuery("FROM AccessPoint point WHERE point.propertyId=:id", AccessPoint.class);
            query.setParameter("id", id);
            AccessPoint accessPoint = query.uniqueResult();

            if (accessPoint == null) {
                throw new RuntimeException("Access Point not found!");
            }

            Query<AccessPointCrud> PrivilegeQuery = session.createQuery("FROM AccessPointCrud a WHERE a.accessPoint=:id", AccessPointCrud.class);
            PrivilegeQuery.setParameter("id", accessPoint);
            return PrivilegeQuery.getResultList();
        }
    }

    @Override
    public List<AccessPointCrud> loadAllAccessPointCruds(String searchText) {
        try (Session session = HibernateUtil.getSession()) {
            Query<AccessPointCrud> query = session.createQuery("SELECT a FROM AccessPointCrud a WHERE a.accessPoint.pointName LIKE :name", AccessPointCrud.class);
            query.setParameter("name", "%" + searchText + "%");
            return query.getResultList();
        }
    }

    @Override
    public boolean removeAllAccessPointCruds(AccessPoint accessPoint) {
        try (Session session = HibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            Query<AccessPointCrud> query = session.createQuery("FROM AccessPointCrud a WHERE a.accessPoint=:id", AccessPointCrud.class);
            query.setParameter("id", accessPoint);
            List<AccessPointCrud> accessPointCruds = query.getResultList();

            if (accessPointCruds != null) {
                for (AccessPointCrud accessPointCrud : accessPointCruds) {
                    session.remove(accessPointCrud);
                }

                transaction.commit();
                return true;
            } else {
                throw new RuntimeException("Access Point Privileges not found!");
            }
        }
    }
}
