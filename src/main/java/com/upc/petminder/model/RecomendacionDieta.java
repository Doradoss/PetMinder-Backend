package com.upc.petminder.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "recomendacion_dieta")
@Data
@NoArgsConstructor
public class RecomendacionDieta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="dieta_id")
    private Dieta dieta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="mascota_id")
    private Mascota mascota;

    public RecomendacionDieta(LocalDate fecha, Dieta dieta, Mascota mascota) {
        this.fecha = fecha;
        this.dieta = dieta;
        this.mascota = mascota;
    }
}
