package com.devstack.pos.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserRole implements SuperEntity {
    @Id
    @Column(name = "property_id")
    private Integer property_id;
    @Column(name = "role_name", nullable = false)
    private String roleName;
    @Column(name = "role_description", nullable = false)
    private String roleDescription;

    @OneToMany(mappedBy = "userRole")
    private Set<User> users;
}
