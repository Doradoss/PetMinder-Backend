package com.upc.petminder.controllers;



import com.upc.petminder.dtos.MascotaDTO.MascotaDto;
import com.upc.petminder.dtos.MascotaDTO.TotalMascotasPorEspecieDto;
import com.upc.petminder.serviceinterfaces.MascotaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/api/user")
public class MascotaController {

    final MascotaService mascotaService;

    public MascotaController(MascotaService mascotaService) {
        this.mascotaService = mascotaService;
    }


    @GetMapping("/findall-mascota")
    public ResponseEntity<List<MascotaDto>> findAll() {
        return ResponseEntity.ok(mascotaService.findAll());
    }

    @GetMapping("/mascota/{id}")
    public ResponseEntity<MascotaDto> findById(@PathVariable Long id) {
        MascotaDto mascotaDto = mascotaService.getMascotaById(id);
        if (mascotaDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(mascotaDto);
    }


    @PostMapping("/registrar-mascota")
    public ResponseEntity<MascotaDto> create(@RequestBody MascotaDto mascotaDto) {
        return new ResponseEntity<>(mascotaService.save(mascotaDto), HttpStatus.CREATED);
    }

    @GetMapping("/totalmascotaespecie")
    public ResponseEntity<TotalMascotasPorEspecieDto> ListaRecordatoriosEnPeriodo (){
        return ResponseEntity.ok(mascotaService.totalMascotasPorEspecieDto());
    }

}
