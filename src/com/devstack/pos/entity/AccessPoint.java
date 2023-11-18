package com.devstack.pos.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

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
    private List<AccessPointCrud> accessPointCruds = new ArrayList<>();
}
