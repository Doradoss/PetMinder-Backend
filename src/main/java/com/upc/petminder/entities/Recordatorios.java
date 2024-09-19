package com.upc.petminder.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "recordatorios")
@Data
@NoArgsConstructor
public class Recordatorios {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "titulo", nullable = false, length = 100)
    private String titulo;
    @Column(name = "descripcion", nullable = false)
    private String descripcion;
    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;
    @Column(name = "hora", nullable = false)
    private LocalTime hora;
    @Column(name = "completado" , nullable = false)
    private Boolean completado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="tipo_recordatorio_id")
    private TipoRecordatorio tipo_recordatorio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="usuario_id")
    private Users users;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="mascota_id")
    private Mascota mascota;

    public Recordatorios(String titulo, String descripcion, LocalDate fecha, TipoRecordatorio tipo_recordatorio, Mascota mascota, Users users, LocalTime hora) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.tipo_recordatorio = tipo_recordatorio;
        this.mascota = mascota;
        this.users = users;
        this.hora = hora;
    }
}
