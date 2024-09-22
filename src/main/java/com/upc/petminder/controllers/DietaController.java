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

    //Listar Dietas
    @GetMapping("/findall-dieta")
    public ResponseEntity<List<DietaDto>> findAll() {
        return ResponseEntity.ok(dietaService.findAll());
    }


    @GetMapping("/dieta/{id}")
    public ResponseEntity<DietaDto> findById(@PathVariable Long id) {
        DietaDto dietaDto = dietaService.getDietaById(id);
        if (dietaDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dietaDto);
    }

    //Registrar Dieta
    @PostMapping("/registrar-dieta")
    @PreAuthorize("hasAuthority('VETERINARY')")
    public ResponseEntity<DietaDto> create(@RequestBody DietaDto dietaDto) {
        return new ResponseEntity<>(dietaService.save(dietaDto), HttpStatus.CREATED);
    }

    //Listar dietas por fecha de asignacion
    @GetMapping("/listar-dieta-fecha")
    public ResponseEntity<List<DietaPorFechaCreacionDto>> listDietaPorFecha(
            @RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return ResponseEntity.ok(dietaService.dietaPorFechaCreacionDtos(fecha));
    }

    // Modificar una dieta
    @PutMapping("/actualizar-dieta/{id}")
    @PreAuthorize("hasAuthority('VETERINARY')")
    public ResponseEntity<DietaDto> update(@PathVariable Long id, @RequestBody DietaDto dietaDto) {
        DietaDto updatedDietaDto = dietaService.update(id, dietaDto);
        return ResponseEntity.ok(updatedDietaDto);  // Devolver el DTO actualizado
    }

    // Eliminar una dieta
    @DeleteMapping("/eliminar-dieta/{id}")
    @PreAuthorize("hasAuthority('VETERINARY')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        dietaService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
