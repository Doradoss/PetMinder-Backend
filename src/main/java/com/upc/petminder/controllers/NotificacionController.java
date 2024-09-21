package com.upc.petminder.controllers;

import com.upc.petminder.dtos.NotificacionDTO.NotificacionDto;
import com.upc.petminder.dtos.NotificacionDTO.NotificacionNoLeidaXusuarioDto;
import com.upc.petminder.dtos.RecomendacionDietaDTO.DietaPorMascotaDto;
import com.upc.petminder.dtos.RecomendacionDietaDTO.RecomendacionDietaDto;
import com.upc.petminder.serviceinterfaces.NotificacionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/user")
public class NotificacionController {
    final NotificacionService notificacionService;

    public NotificacionController(NotificacionService notificacionService) {
        this.notificacionService = notificacionService;
    }

    @PostMapping("/notificacion")
    public ResponseEntity<NotificacionDto> create(@RequestBody NotificacionDto notificacionDto) {
        return new ResponseEntity<>(notificacionService.save(notificacionDto), HttpStatus.CREATED);
    }

    //Notificacion sin leer
    @GetMapping("/notificaciones-sin-leer")
    public ResponseEntity<List<NotificacionNoLeidaXusuarioDto>> buscarNotificacionesnoLeidas(
            @RequestParam("user_id") Integer user_id) {
        return ResponseEntity.ok(notificacionService.noLeidaXusuario(user_id));
    }
}
