package com.upc.petminder.controllers;

import com.upc.petminder.dtos.DietaDTO.DietaDto;
import com.upc.petminder.dtos.DietaDTO.DietaPorFechaCreacionDto;
import com.upc.petminder.serviceinterfaces.DietaService;
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
public class DietaController {

    final DietaService dietaService;

    public DietaController(DietaService dietaService) {
        this.dietaService = dietaService;
    }

    @PostMapping("/registrar-dieta")
    @PreAuthorize("hasAuthority('VETERINARY')")
    public ResponseEntity<DietaDto> create(@RequestBody DietaDto dietaDto) {
        return new ResponseEntity<>(dietaService.save(dietaDto), HttpStatus.CREATED);
    }

    @GetMapping("/listar-dieta-fecha")
    public ResponseEntity<List<DietaPorFechaCreacionDto>> listDietaPorFecha(
            @RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return ResponseEntity.ok(dietaService.dietaPorFechaCreacionDtos(fecha));
    }

}
