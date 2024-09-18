package com.upc.petminder.dtos.DietaDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DietaMascotaPorFechaCreacionDto {

    private String nombreDieta;
    private String indicacionesDieta;
}
