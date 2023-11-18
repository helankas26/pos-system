package com.devstack.pos.bo.custom;

import com.devstack.pos.bo.SuperBo;
import com.devstack.pos.dto.AccessPointDto;

public interface AccessPointBo extends SuperBo {
    boolean createAccessPoint(AccessPointDto dto);
}
