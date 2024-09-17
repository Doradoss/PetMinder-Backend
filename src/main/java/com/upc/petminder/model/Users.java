package com.upc.petminder.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre", nullable = false)
    private String nombre;
    @Column(name = "apellidos", nullable = false)
    private String apellidos;
    @Column(name = "celular", nullable = false, length = 9)
    private String celular;
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "username", nullable = false, length = 30, unique = true)
    private String username;
    @Column(name = "password", nullable = false, length = 200)
    private String password;
    private Boolean enabled;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<Role> roles;

    @OneToMany(mappedBy = "users", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Recordatorios> recordatorios;

    @OneToMany(mappedBy = "users", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Notificacion> notificaciones;

    @OneToMany(mappedBy = "users", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Mascota> mascotas;


    public Users(String nombre, String apellidos, String celular, String email, String username, String password, Boolean enabled, List<Role> roles) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.celular = celular;
        this.email = email;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.roles = roles;
    }
}
