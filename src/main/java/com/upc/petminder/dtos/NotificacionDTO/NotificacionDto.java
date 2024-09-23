package com.upc.petminder.dtos.NotificacionDTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificacionDto {
    private Long id;
    private String titulo;
    private String mensaje;
    private Boolean leido;
    private LocalDate fechaCreacion;

    private Long usuario_id;
}

