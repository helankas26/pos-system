package com.devstack.pos.bo.custom.impl;

import com.devstack.pos.bo.custom.UserBo;
import com.devstack.pos.dao.DaoFactory;
import com.devstack.pos.dao.custom.UserRoleDao;
import com.devstack.pos.entity.User;
import com.devstack.pos.entity.UserRole;
import com.devstack.pos.util.HibernateUtil;
import com.devstack.pos.util.KeyGenerator;
import com.devstack.pos.util.PasswordGenerator;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UserBoImpl implements UserBo {
    UserRoleDao userRoleDao= DaoFactory.getDao(DaoFactory.DaoType.USER_ROLE);
    @Override
    public void initializeSystem() {
        if (!userRoleDao.isExists()){

            try(Session session = HibernateUtil.getSession()) {
                Transaction transaction = session.beginTransaction();

                UserRole adminRole = new UserRole();
                UserRole userRole = new UserRole();

                adminRole.setPropertyId(KeyGenerator.generateId());
                adminRole.setRoleName("ADMIN");
                adminRole.setRoleDescription("Only for the Admin");

                userRole.setPropertyId(KeyGenerator.generateId());
                userRole.setRoleName("USER");
                userRole.setRoleDescription("Only for the User");

                // ========================================================
                User systemUser = new User();
                systemUser.setPropertyId(KeyGenerator.generateId());
                systemUser.setUsername("helankas26@gmail.com");
                systemUser.setPassword(PasswordGenerator.passwordGen(6));
                systemUser.setDisplayName("Helanka S");
                systemUser.setActiveState(true);
                systemUser.setUserRole(adminRole);

                session.save(adminRole);
                session.save(userRole);
                session.save(systemUser);

                transaction.commit();
            }
        }
    }
}
