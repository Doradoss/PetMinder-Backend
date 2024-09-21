package com.upc.petminder.dtos.RecomendacionDietaDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DietaPorMascotaDto {
    private String nombreDieta;
    private String indicaciones;

}
