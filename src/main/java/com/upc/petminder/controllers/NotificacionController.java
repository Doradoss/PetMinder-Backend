package com.upc.petminder.controllers;


import com.upc.petminder.dtos.NotificacionDTO.NotificacionDto;
import com.upc.petminder.dtos.NotificacionDTO.NotificacionNoLeidaXusuarioDto;
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
    //Listar Notificaciones
    @GetMapping("/findall-notificacion")
    public ResponseEntity<List<NotificacionDto>> findAll() {
        return ResponseEntity.ok(notificacionService.findAll());
    }

    //Listar Notificaciones por Id
    @GetMapping("/notificacion/{id}")
    public ResponseEntity<NotificacionDto> findById(@PathVariable Long id) {
        NotificacionDto notificacionDto = notificacionService.getNotificacionById(id);
        if (notificacionDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(notificacionDto);
    }
    //Registrar notificacion
    @PostMapping("/registrar-notificacion")
    public ResponseEntity<NotificacionDto> create(@RequestBody NotificacionDto notificacionDto) {
        return new ResponseEntity<>(notificacionService.save(notificacionDto), HttpStatus.CREATED);
    }

    //Listar Notificaciones sin leer
    @GetMapping("/notificacion-sin-leer")
    public ResponseEntity<List<NotificacionNoLeidaXusuarioDto>> buscarNotificacionesnoLeidas(
            @RequestParam("user_id") Integer user_id) {
        return ResponseEntity.ok(notificacionService.noLeidaXusuario(user_id));
    }

    //Modificar notificaciones
    @PutMapping("/actualizar-notificacion/{id}")
    public ResponseEntity<NotificacionDto> update(@PathVariable Long id, @RequestBody NotificacionDto notificacionDto) {
        NotificacionDto updatedNotificacionDto = notificacionService.update(id, notificacionDto);
        return ResponseEntity.ok(updatedNotificacionDto);  // Devolver el DTO actualizado
    }
    //Eliminar notificaciones
    @DeleteMapping("/eliminar-notificacion/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        notificacionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
