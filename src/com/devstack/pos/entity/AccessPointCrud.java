package com.devstack.pos.entity;

import com.devstack.pos.enums.Crud;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AccessPointCrud implements SuperEntity {
    @Id
    @Column(name = "property_id")
    private Long propertyId;
    @Enumerated(EnumType.STRING)
    private Crud crud;

    @ManyToOne
    @JoinColumn(name = "access_point_id")
    private AccessPoint accessPoint;
}
