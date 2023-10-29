package com.devstack.pos.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @Column(name = "property_id")
    private Long propertyId;
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "display_name", nullable = false)
    private String displayName;
    @Column(name = "active_state")
    private boolean activeState;

    @ManyToOne
    @JoinColumn(name = "user_role_id")
    private UserRole userRole;
}
