package com.upc.petminder.dtos.RecomendacionDietaDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class DietaPorMascotaDto {
    private String nombreDieta;
    private String indicaciones;

}
