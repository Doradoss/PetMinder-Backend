package com.upc.petminder.dtos.RecomendacionDietaDTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecomendacionDietaDto {
    private Long id;
    private LocalDate fecha;
    private Long dieta_id;
    private Long mascota_id;

}
