package com.upc.petminder.dtos.NotificacionDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
//Como se muestra la consulta
public class NotificacionNoLeidaXusuarioDto {
    private String titulo;
    private String mensaje;
    private Date fecha_creacion;
}
