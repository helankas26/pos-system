package com.devstack.pos.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AccessPoint implements SuperEntity {
    @Id
    @Column(name = "property_id")
    private Long propertyId;
    @Column(name="point_name")
    private String pointName;

    @OneToMany(mappedBy = "accessPoint")
    private Set<AccessPointCrud> accessPointCruds;
}
