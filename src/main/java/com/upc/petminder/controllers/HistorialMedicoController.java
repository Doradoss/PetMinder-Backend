package com.upc.petminder.controllers;

import com.upc.petminder.dtos.HistorialMedicoDTO.HistorialMedicoDto;
import com.upc.petminder.dtos.HistorialMedicoDTO.HistorialMedicoPorMascotaYFechaDto;
import com.upc.petminder.entities.HistorialMedico;
import com.upc.petminder.serviceinterfaces.HistorialMedicoService;
import org.modelmapper.ModelMapper;
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

    @GetMapping("/findall-historialmedico")
    public ResponseEntity<List<HistorialMedicoDto>> findAll() {
        return ResponseEntity.ok(historialMedicoService.findAll());
    }

    @GetMapping("/historial/{id}")
    public ResponseEntity<HistorialMedicoDto> findById(@PathVariable Long id) {
        HistorialMedicoDto historialMedicoDto = historialMedicoService.getHistorialMedicoById(id);
        if (historialMedicoDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(historialMedicoDto);
    }

    @PostMapping("/registrar-historial-medico") //Problemas con el id mascota
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

    @PutMapping("/historialmedico/{id}")
    public void updateHistorialMedico(@RequestBody HistorialMedicoDto dto) {
        ModelMapper m = new ModelMapper();
        HistorialMedico historialMedico = m.map(dto, HistorialMedico.class);
        historialMedicoService.insert(historialMedico);
    }

    @DeleteMapping("/historialdelete/{id}")
    public void delete(@PathVariable("id") Long id)
    {
        historialMedicoService.delete(id);
    }


}
