package com.upc.petminder.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "tipo_recordatorio")
@Data
@NoArgsConstructor
public class TipoRecordatorio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;
    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @OneToMany(mappedBy = "tipo_recordatorio", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Recordatorios> recordatorios;

    public TipoRecordatorio(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }
}
