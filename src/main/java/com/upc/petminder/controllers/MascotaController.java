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

    //Listar todas las Mascotas existentes
    @GetMapping("/findall-mascota")
    public ResponseEntity<List<MascotaDto>> findAll() {
        return ResponseEntity.ok(mascotaService.findAll());
    }

    //Listar las Mascotas por id
    @GetMapping("/mascota/{id}")
    public ResponseEntity<MascotaDto> findById(@PathVariable Long id) {
        MascotaDto mascotaDto = mascotaService.getMascotaById(id);
        if (mascotaDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(mascotaDto);
    }

    //Registrar Mascota
    @PostMapping("/registrar-mascota")
    public ResponseEntity<MascotaDto> create(@RequestBody MascotaDto mascotaDto) {
        return new ResponseEntity<>(mascotaService.save(mascotaDto), HttpStatus.CREATED);
    }

    //Obtiene el total de especie unicas de Mascota
    @GetMapping("/total-mascota-especie")
    public ResponseEntity<TotalMascotasPorEspecieDto> ListaTotalMascotasPorEspecie (){
        return ResponseEntity.ok(mascotaService.totalMascotasPorEspecieDto());
    }

    //Modificar Mascota
    @PutMapping("/actualizar-mascota/{id}")
    public ResponseEntity<MascotaDto> update(@PathVariable Long id, @RequestBody MascotaDto mascotaDto) {
        MascotaDto updatedMascotaDto = mascotaService.update(id, mascotaDto);
        return ResponseEntity.ok(updatedMascotaDto);  // Devolver el DTO actualizado
    }
    //Eliminar Mascota
    @DeleteMapping("/eliminar-mascota/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        mascotaService.delete(id);
        return ResponseEntity.noContent().build();  // Devolver un status 204 No Content
    }

}
