package com.upc.petminder.dtos.TipoRecordatorioDTO;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoRecordatorioDto {
    private Long id;
    private String nombre;
    private String descripcion;
}
