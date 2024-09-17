package com.upc.petminder.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "historial_medico")
@Data
@NoArgsConstructor
public class HistorialMedico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "descripcion", nullable = false)
    private String descripcion;
    @Column(name = "diagnostico", nullable = false, length = 100)
    private String diagnostico;
    @Column(name = "tratamiento", nullable = false, length = 100)
    private String tratamiento;
    @Column(name = "fecha", nullable = false, length = 100)
    private LocalDate fecha;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="mascota_id")
    private Mascota mascota;

    public HistorialMedico(String descripcion, String diagnostico, String tratamiento, LocalDate fecha, Mascota mascota) {
        this.descripcion = descripcion;
        this.diagnostico = diagnostico;
        this.tratamiento = tratamiento;
        this.fecha = fecha;
        this.mascota = mascota;
    }
}
