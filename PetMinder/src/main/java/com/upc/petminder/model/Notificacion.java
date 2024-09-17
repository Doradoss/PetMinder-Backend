package com.upc.petminder.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "notificacion")
@Data
@NoArgsConstructor
public class Notificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "titulo", nullable = false)
    private String titulo;
    @Column(name = "mensaje", nullable = false)
    private String mensaje;
    @Column(name = "leido", nullable = false)
    private Boolean leido;
    @Column(name = "fechaCreacion", nullable = false)
    private LocalDate fechaCreacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="usuario_id")
    private Users users;

    public Notificacion(String titulo, String mensaje, Boolean leido, LocalDate fechaCreacion, Users users) {
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.leido = leido;
        this.fechaCreacion = fechaCreacion;
        this.users = users;
    }
}
