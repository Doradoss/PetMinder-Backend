package com.upc.petminder.dtos.HistorialMedicoDTO;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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
