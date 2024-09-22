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

    @GetMapping("/findall-tiporecordatorio")
    public ResponseEntity<List<TipoRecordatorioDto>> findAll() {
        return ResponseEntity.ok(tipoRecordatorioService.findAll());
    }

    @GetMapping("/tiporecordatorio/{id}")
    public ResponseEntity<TipoRecordatorioDto> findById(@PathVariable Long id) {
        TipoRecordatorioDto tipoRecordatorioDto = tipoRecordatorioService.getTipoRecordatorioById(id);
        if (tipoRecordatorioDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tipoRecordatorioDto);
    }

    @PostMapping("/registrar-tipo-recordatorio")
    @PreAuthorize("hasAuthority('VETERINARY')")
    public ResponseEntity<TipoRecordatorioDto> create(@RequestBody TipoRecordatorioDto tipoRecordatorioDto) {
        return new ResponseEntity<>(tipoRecordatorioService.save(tipoRecordatorioDto), HttpStatus.CREATED);
    }
}
