package com.upc.petminder.controllers;


import com.upc.petminder.dtos.RecomendacionDietaDTO.DietaPorMascotaDto;
import com.upc.petminder.dtos.RecomendacionDietaDTO.DietaPorMascotaYFechaDto;
import com.upc.petminder.dtos.RecomendacionDietaDTO.RecomendacionDietaDto;;
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

    //Listar todas las Recomendaciones de Dieta
    @GetMapping("/findall-recomendacion-dieta")
    public ResponseEntity<List<RecomendacionDietaDto>> findAll() {
        return ResponseEntity.ok(recomendacionDietaService.findAll());
    }

    //Listar Recomendaciones de Dieta por Id
    @GetMapping("/recomendacion-dieta/{id}")
    public ResponseEntity<RecomendacionDietaDto> findById(@PathVariable Long id) {
        RecomendacionDietaDto recomendacionDietaDto = recomendacionDietaService.getRecomendacionDietaById(id);
        if (recomendacionDietaDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recomendacionDietaDto);
    }

    //Registrar recomendacion dieta
    @PostMapping("/registrar-recomendacion-dieta")
    public ResponseEntity<RecomendacionDietaDto> create(@RequestBody RecomendacionDietaDto recomendacionDietaDto) {
        return new ResponseEntity<>(recomendacionDietaService.save(recomendacionDietaDto), HttpStatus.CREATED);
    }

    //Dietas recomendadas por fecha y mascota
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

    // Modificar una recomendación de dieta
    @PutMapping("/actualizar-recomendacion-dieta/{id}")
    public ResponseEntity<RecomendacionDietaDto> updateRecomendacionDieta(
            @PathVariable Long id,
            @RequestBody RecomendacionDietaDto recomendacionDietaDto) {

        RecomendacionDietaDto updatedRecomendacion = recomendacionDietaService.update(id, recomendacionDietaDto);
        return ResponseEntity.ok(updatedRecomendacion);
    }


    // Eliminar una recomendación de dieta
    @DeleteMapping("/eliminar-recomendacion-dieta/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        recomendacionDietaService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
