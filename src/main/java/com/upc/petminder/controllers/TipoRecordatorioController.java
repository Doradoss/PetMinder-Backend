package com.upc.petminder.controllers;


import com.upc.petminder.dtos.TipoRecordatorioDTO.TipoRecordatorioDto;
import com.upc.petminder.serviceinterfaces.TipoRecordatorioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/api/user")
public class TipoRecordatorioController {
    final TipoRecordatorioService tipoRecordatorioService;

    public TipoRecordatorioController(TipoRecordatorioService tipoRecordatorioService) {
        this.tipoRecordatorioService = tipoRecordatorioService;
    }

    //Listar todos los Tipos de Recordatorio
    @GetMapping("/findall-tipo-recordatorio")
    public ResponseEntity<List<TipoRecordatorioDto>> findAll() {
        return ResponseEntity.ok(tipoRecordatorioService.findAll());
    }

    //Listar los Tipos de Recordatorio por Id
    @GetMapping("/tipo-recordatorio/{id}")
    public ResponseEntity<TipoRecordatorioDto> findById(@PathVariable Long id) {
        TipoRecordatorioDto tipoRecordatorioDto = tipoRecordatorioService.getTipoRecordatorioById(id);
        if (tipoRecordatorioDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tipoRecordatorioDto);
    }

    //Registrar Tipos de Recordatorio
    @PostMapping("/registrar-tipo-recordatorio")
    @PreAuthorize("hasAuthority('VETERINARY')")
    public ResponseEntity<TipoRecordatorioDto> create(@RequestBody TipoRecordatorioDto tipoRecordatorioDto) {
        return new ResponseEntity<>(tipoRecordatorioService.save(tipoRecordatorioDto), HttpStatus.CREATED);
    }

    // Modificar Tipo Recordatorio
    @PutMapping("/actualizar-tipo-recordatorio/{id}")
    @PreAuthorize("hasAuthority('VETERINARY')")
    public ResponseEntity<TipoRecordatorioDto> update(@PathVariable Long id, @RequestBody TipoRecordatorioDto tipoRecordatorioDto) {
        try {
            TipoRecordatorioDto updatedTipo = tipoRecordatorioService.update(id, tipoRecordatorioDto);
            return ResponseEntity.ok(updatedTipo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar Tipo Recordatorio
    @DeleteMapping("/eliminar-tipo-recordatorio/{id}")
    @PreAuthorize("hasAuthority('VETERINARY')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            tipoRecordatorioService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
