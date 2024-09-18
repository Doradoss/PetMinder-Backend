package com.upc.petminder.controllers;

import com.upc.petminder.dtos.RecordatoriosDto;
import com.upc.petminder.dtos.RecordatoriosPorPeriodoDeFechasDto;
import com.upc.petminder.entities.Recordatorios;
import com.upc.petminder.serviceinterfaces.RecordatoriosService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/user/")
public class RecordatoriosController {
    final RecordatoriosService recordatoriosService;

    public RecordatoriosController(RecordatoriosService recordatoriosService) {
        this.recordatoriosService = recordatoriosService;
    }

    @PostMapping("registrar-recordatorio")
    @PreAuthorize("hasAuthority('VETERINARY')")
    public ResponseEntity<RecordatoriosDto> create(@RequestBody RecordatoriosDto recordatoriosDto) {
        return new ResponseEntity<>(recordatoriosService.save(recordatoriosDto), HttpStatus.CREATED);
    }
    @GetMapping("recordatorio-periodo")
    public ResponseEntity<List<RecordatoriosPorPeriodoDeFechasDto>> ListaRecordatoriosEnPeriodo (
            @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ){
        return ResponseEntity.ok(recordatoriosService.recordatoriosPorPeriodo(from, to));
    }
}
