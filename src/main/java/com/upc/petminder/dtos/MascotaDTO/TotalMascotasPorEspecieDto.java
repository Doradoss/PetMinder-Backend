package com.upc.petminder.dtos.MascotaDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TotalMascotasPorEspecieDto {
    private Long cantidad_mascotas;
}
