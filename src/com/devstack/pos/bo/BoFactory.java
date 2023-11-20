package com.devstack.pos.bo;

import com.devstack.pos.bo.custom.impl.AccessPointBoImpl;
import com.devstack.pos.bo.custom.impl.AccessPointCrudBoImpl;
import com.devstack.pos.bo.custom.impl.UserBoImpl;
import com.devstack.pos.bo.custom.impl.UserRoleBoImpl;

public class BoFactory {
    private BoFactory() {
    }

    public enum BoType {
        USER, USER_ROLE, ACCESS_POINT, ACCESS_POINT_CRUD
    }

    public static <T> T getBo(BoType type) {
        switch (type) {
            case USER:
                return (T) new UserBoImpl();
            case USER_ROLE:
                return (T) new UserRoleBoImpl();
            case ACCESS_POINT:
                return (T) new AccessPointBoImpl();
            case ACCESS_POINT_CRUD:
                return (T) new AccessPointCrudBoImpl();
            default:
                return null;
        }
    }
}
