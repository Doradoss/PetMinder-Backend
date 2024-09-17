package com.upc.petminder.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecordatoriosPorPeriodoDeFechasDto {
    private String titulo;
    private String descripcion;
    private Date fecha;
    private Time hora;
}
