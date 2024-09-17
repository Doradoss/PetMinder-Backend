package com.upc.petminder.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "dieta")
@Data
@NoArgsConstructor
public class Dieta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;
    @Column(name = "indicaciones", nullable = false)
    private String indicaciones;
    @Column(name = "fecha_creacion", nullable = false)
    private LocalDate fecha_creacion;

    @OneToMany(mappedBy = "dieta", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<RecomendacionDieta> recomendaciones_dieta;


}
