package com.upc.petminder.controllers;

import com.upc.petminder.dtos.HistorialMedicoDTO.HistorialMedicoDto;
import com.upc.petminder.dtos.HistorialMedicoDTO.HistorialMedicoPorMascotaYFechaDto;
import com.upc.petminder.serviceinterfaces.HistorialMedicoService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/user")
public class HistorialMedicoController {
    final HistorialMedicoService historialMedicoService;

    public HistorialMedicoController(HistorialMedicoService historialMedicoService) {
        this.historialMedicoService = historialMedicoService;
    }

    @PostMapping("/registrar-historial-medico")
    @PreAuthorize("hasAuthority('VETERINARY')")
    public ResponseEntity<HistorialMedicoDto> create(@RequestBody HistorialMedicoDto historialMedicoDto) {
        return new ResponseEntity<>(historialMedicoService.save(historialMedicoDto), HttpStatus.CREATED);
    }

    @GetMapping("/historial-medico-mascota-fecha")
    public ResponseEntity<List<HistorialMedicoPorMascotaYFechaDto>> listHistorialMedicoPorMascotaYFecha(
            @RequestParam("mascotaId") Long mascotaId,
            @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return ResponseEntity.ok(historialMedicoService.historialMedicoPorMascotaYFecha(mascotaId, from, to));
    }
}
