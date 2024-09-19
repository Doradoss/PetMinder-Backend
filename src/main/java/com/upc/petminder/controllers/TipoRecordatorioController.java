package com.upc.petminder.controllers;

import com.upc.petminder.dtos.RecordatoriosDTO.RecordatoriosDto;
import com.upc.petminder.dtos.TipoRecordatorioDTO.TipoRecordatorioDto;
import com.upc.petminder.serviceinterfaces.RecordatoriosService;
import com.upc.petminder.serviceinterfaces.TipoRecordatorioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/user/")
public class TipoRecordatorioController {
    final TipoRecordatorioService tipoRecordatorioService;

    public TipoRecordatorioController(TipoRecordatorioService tipoRecordatorioService) {
        this.tipoRecordatorioService = tipoRecordatorioService;
    }

    @PostMapping("registrar-tipo-recordatorio")
    @PreAuthorize("hasAuthority('VETERINARY')")
    public ResponseEntity<TipoRecordatorioDto> create(@RequestBody TipoRecordatorioDto tipoRecordatorioDto) {
        return new ResponseEntity<>(tipoRecordatorioService.save(tipoRecordatorioDto), HttpStatus.CREATED);
    }
}
