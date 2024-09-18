package com.upc.petminder.dtos.DietaDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DietaDto {

    private Long id;
    private String nombre;
    private String indicaciones;
    private LocalDate fecha_creacion;
}
