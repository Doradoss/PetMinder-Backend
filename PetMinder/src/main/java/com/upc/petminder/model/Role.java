package com.upc.petminder.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "roles", uniqueConstraints = { @UniqueConstraint(columnNames = { "user_id", "rol" }) })
@Data
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String rol;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private Users user;

    public Role(String rol, Users user) {
        this.rol = rol;
        this.user = user;
    }
}
