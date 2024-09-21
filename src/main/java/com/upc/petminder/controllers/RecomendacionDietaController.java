package com.upc.petminder.controllers;


import com.upc.petminder.dtos.RecomendacionDietaDTO.DietaPorMascotaDto;
import com.upc.petminder.dtos.RecomendacionDietaDTO.DietaPorMascotaYFechaDto;
import com.upc.petminder.dtos.RecomendacionDietaDTO.RecomendacionDietaDto;
import com.upc.petminder.serviceinterfaces.RecomendacionDietaService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/user")
public class RecomendacionDietaController {

    final RecomendacionDietaService recomendacionDietaService;

    public RecomendacionDietaController(RecomendacionDietaService recomendacionDietaService) {
        this.recomendacionDietaService = recomendacionDietaService;
    }

    @PostMapping("/recomendacion-dieta")
    public ResponseEntity<RecomendacionDietaDto> create(@RequestBody RecomendacionDietaDto recomendacionDietaDto) {
        return new ResponseEntity<>(recomendacionDietaService.save(recomendacionDietaDto), HttpStatus.CREATED);
    }

    @GetMapping("/dieta-mascota-fecha")
    public ResponseEntity<List<DietaPorMascotaYFechaDto>> listDietaMascotaFecha(
            @RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @RequestParam("mascotaId") Integer mascotaId) {
        return ResponseEntity.ok(recomendacionDietaService.dietaPorMascotaYFechas(mascotaId, fecha));
    }

    //Dieta Por Mascota
    @GetMapping("/listar-dieta-mascota")
    public ResponseEntity<List<DietaPorMascotaDto>> buscarDietaPorMascota(
            @RequestParam("mascotaId") Integer mascotaId) {
        return ResponseEntity.ok(recomendacionDietaService.DietasPorMascota(mascotaId));
    }

}
