package com.upc.petminder.dtos.MascotaDTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MascotaDto {
    private Long id;
    private String nombre;
    private String especie;
    private String raza;
    private Integer edad;
    private Long usuario_id;
}
