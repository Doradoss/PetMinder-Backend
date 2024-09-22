package com.upc.petminder.dtos.HistorialMedicoDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistorialMedicoPorMascotaYFechaDto {
    private String descripcion;
    private String diagnostico;
    private String tratamiento;
    private Date fecha;
}
