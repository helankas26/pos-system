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
public class User {
    @Id
    @Column(name = "property_id")
    private Integer property_id;
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "display_name", nullable = false)
    private String displayName;
    @Column(name = "active_state")
    private String activeState;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Set<UserRole> userRoles;
}
