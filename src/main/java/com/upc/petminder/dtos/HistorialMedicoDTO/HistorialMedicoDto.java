package com.upc.petminder.dtos.HistorialMedicoDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistorialMedicoDto {
    private Long id;
    private String descripcion;
    private String diagnostico;
    private String tratamiento;
    private LocalDate fecha;
    private Long mascota_id;
}
