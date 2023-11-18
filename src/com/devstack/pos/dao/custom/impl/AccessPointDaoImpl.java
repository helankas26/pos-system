package com.devstack.pos.dao.custom.impl;

import com.devstack.pos.dao.custom.AccessPointDao;
import com.devstack.pos.entity.AccessPoint;
import com.devstack.pos.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class AccessPointDaoImpl implements AccessPointDao {
    @Override
    public boolean create(AccessPoint accessPoint) {
        try (Session session = HibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(accessPoint);
            transaction.commit();
            return true;
        }
    }

    @Override
    public AccessPoint find(Long aLong) {
        return null;
    }

    @Override
    public boolean remove(Long id) {
        try (Session session = HibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            Query<AccessPoint> query = session.createQuery("FROM AccessPoint point WHERE point.propertyId=:id", AccessPoint.class);
            query.setParameter("id", id);
            AccessPoint accessPoint = query.uniqueResult();

            if (accessPoint != null) {
                session.remove(accessPoint);
                transaction.commit();
                return true;
            } else {
                throw new RuntimeException("Access Point not found!");
            }
        }
    }

    @Override
    public boolean modify(AccessPoint accessPoint) {
        try (Session session = HibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            Query<AccessPoint> query = session.createQuery("FROM AccessPoint point WHERE point.propertyId=:id", AccessPoint.class);
            query.setParameter("id", accessPoint.getPropertyId());
            AccessPoint selectedAccessPoint = query.uniqueResult();

            if (selectedAccessPoint != null) {
                selectedAccessPoint.setPointName(accessPoint.getPointName());

                session.update(selectedAccessPoint);
                transaction.commit();
                return true;
            } else {
                throw new RuntimeException("User Role not found!");
            }
        }
    }

    @Override
    public List<AccessPoint> loadAll() {
        try(Session session= HibernateUtil.getSession()){
            Query<AccessPoint> accessPointQuery = session.createQuery("FROM AccessPoint ", AccessPoint.class);
            return accessPointQuery.list();
        }
    }

    @Override
    public List<AccessPoint> loadAllAccessPoints(String searchText) {
        try (Session session = HibernateUtil.getSession()) {
            Query<AccessPoint> userQuery = session.createQuery("SELECT point FROM AccessPoint point WHERE point.pointName LIKE :name", AccessPoint.class);
            userQuery.setParameter("name", "%" + searchText + "%");
            return userQuery.getResultList();
        }
    }
}
