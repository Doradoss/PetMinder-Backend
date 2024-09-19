package com.upc.petminder.dtos.RecomendacionDietaDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DietaPorMascotaYFechaDto {
    private String nombreDieta;
    private String indicaciones;
}
