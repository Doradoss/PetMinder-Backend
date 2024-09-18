package com.upc.petminder.controllers;

import com.upc.petminder.dtos.MascotaDTO.MascotaDto;
import com.upc.petminder.dtos.MascotaDTO.TotalMascotasPorEspecieDto;
import com.upc.petminder.serviceinterfaces.MascotaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/user")
public class MascotaController {

    final MascotaService mascotaService;

    public MascotaController(MascotaService mascotaService) {
        this.mascotaService = mascotaService;
    }

    @PostMapping("/registrar-mascota")
    public ResponseEntity<MascotaDto> create(@RequestBody MascotaDto mascotaDto) {
        return new ResponseEntity<>(mascotaService.save(mascotaDto), HttpStatus.CREATED);
    }

    @GetMapping("/totalMascotaEspecie")
    public ResponseEntity<TotalMascotasPorEspecieDto> ListaRecordatoriosEnPeriodo (){
        return ResponseEntity.ok(mascotaService.totalMascotasPorEspecieDto());
    }

}
