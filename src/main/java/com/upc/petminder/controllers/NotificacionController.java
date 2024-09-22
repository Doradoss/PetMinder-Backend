package com.upc.petminder.controllers;


import com.upc.petminder.dtos.NotificacionDTO.NotificacionDto;
import com.upc.petminder.dtos.NotificacionDTO.NotificacionNoLeidaXusuarioDto;
import com.upc.petminder.entities.Notificacion;
import com.upc.petminder.serviceinterfaces.NotificacionService;
import org.modelmapper.ModelMapper;
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

    @GetMapping("/findall-notificacion")
    public ResponseEntity<List<NotificacionDto>> findAll() {
        return ResponseEntity.ok(notificacionService.findAll());
    }

    @GetMapping("/notificacion/{id}")
    public ResponseEntity<NotificacionDto> findById(@PathVariable Long id) {
        NotificacionDto notificacionDto = notificacionService.getNotificacionById(id);
        if (notificacionDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(notificacionDto);
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

    @PutMapping("/notificaciones/{id}")
    public void updateNotificacion(@RequestBody NotificacionDto dto) {
        ModelMapper m = new ModelMapper();
        Notificacion notificacion = m.map(dto, Notificacion.class);
        notificacionService.insert(notificacion);
    }

    @DeleteMapping("/notificaciondelete/{id}")
    public void delete(@PathVariable("id") Long id){
        notificacionService.delete(id);
    }
}
