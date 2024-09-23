package com.upc.petminder.dtos.RecordatoriosDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecordatoriosDto {
    private Long id;
    private String titulo;
    private String descripcion;
    private LocalDate fecha;
    private LocalTime hora;
    private Boolean completado;

    private Long tipo_recordatorio_id;
    private Long usuario_id;
    private Long mascota_id;
}
