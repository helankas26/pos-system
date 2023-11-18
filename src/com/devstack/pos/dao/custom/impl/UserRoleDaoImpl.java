package com.devstack.pos.dao.custom.impl;

import com.devstack.pos.dao.custom.UserRoleDao;
import com.devstack.pos.entity.UserRole;
import com.devstack.pos.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class UserRoleDaoImpl implements UserRoleDao {
    @Override
    public boolean create(UserRole userRole) {
        try (Session session = HibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(userRole);
            transaction.commit();
        }
        return true;
    }

    @Override
    public UserRole find(Long id) {
        return null;
    }

    @Override
    public boolean remove(Long id) {
        try (Session session = HibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            Query<UserRole> query = session.createQuery("FROM UserRole role WHERE role.propertyId=:id", UserRole.class);
            query.setParameter("id", id);
            UserRole userRole = query.uniqueResult();

            if (userRole != null) {
                session.remove(userRole);
                transaction.commit();
                return true;
            } else {
                throw new RuntimeException("User Role not found!");
            }
        }
    }

    @Override
    public boolean modify(UserRole userRole) {
        try (Session session = HibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            Query<UserRole> query = session.createQuery("FROM UserRole role WHERE role.propertyId=:id", UserRole.class);
            query.setParameter("id", userRole.getPropertyId());
            UserRole selectedUserRole = query.uniqueResult();

            if (selectedUserRole != null) {
                selectedUserRole.setRoleName(userRole.getRoleName());
                selectedUserRole.setRoleDescription(userRole.getRoleDescription());

                session.update(selectedUserRole);
                transaction.commit();
                return true;
            } else {
                throw new RuntimeException("User Role not found!");
            }
        }
    }

    @Override
    public List<UserRole> loadAll() {
        try(Session session= HibernateUtil.getSession()){
            Query<UserRole> userRoleQuery = session.createQuery("FROM UserRole", UserRole.class);
            return userRoleQuery.list();
        }
    }

    @Override
    public boolean isExists() {
        try (Session session = HibernateUtil.getSession()) {
            /*Criteria criteria = session.createCriteria(UserRole.class);
            criteria.setProjection(Projections.rowCount());
            Long count = (Long) criteria.uniqueResult();*/

            Query query = session.createQuery("SELECT COUNT(*) FROM UserRole");
            Long count = (Long) query.getSingleResult();

            return count > 0;
        }
    }

    @Override
    public List<UserRole> loadAllUserRoles(String searchText) {
        try (Session session = HibernateUtil.getSession()) {
            Query<UserRole> userQuery = session.createQuery("SELECT role FROM UserRole role WHERE role.roleName LIKE :name", UserRole.class);
            userQuery.setParameter("name", "%" + searchText + "%");
            return userQuery.getResultList();
        }
    }
}
