package com.upc.petminder.controllers;


import com.upc.petminder.dtos.RecordatoriosDTO.*;
import com.upc.petminder.entities.Recordatorios;
import com.upc.petminder.serviceinterfaces.RecordatoriosService;
import org.modelmapper.ModelMapper;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/api/user")
public class RecordatoriosController {
    final RecordatoriosService recordatoriosService;

    public RecordatoriosController(RecordatoriosService recordatoriosService) {
        this.recordatoriosService = recordatoriosService;
    }
    //Listar recordatorios
    @GetMapping("/findall-recordatorios")
    public ResponseEntity<List<RecordatoriosDto>> findAll() {
        return ResponseEntity.ok(recordatoriosService.findAll());
    }

    @GetMapping("/recordatorio/{id}")
    public ResponseEntity<RecordatoriosDto> findById(@PathVariable Long id) {
        RecordatoriosDto recordatorios = recordatoriosService.getRecordatoriosById(id);
        if (recordatorios == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recordatorios);
    }

    @PostMapping("registrar-recordatorio")
    public ResponseEntity<RecordatoriosDto> create(@RequestBody RecordatoriosDto recordatoriosDto) {
        return new ResponseEntity<>(recordatoriosService.save(recordatoriosDto), HttpStatus.CREATED);
    }
    //Mostrar recordatprios entre un periodo de fechas
    @GetMapping("recordatorio-periodo")
    public ResponseEntity<List<RecordatoriosPorPeriodoDeFechasDto>> ListaRecordatoriosEnPeriodo (
            @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ){
        return ResponseEntity.ok(recordatoriosService.recordatoriosPorPeriodo(from, to));
    }
    //Mostrar recordatorios por tipo de recordatorio
    @GetMapping("recordatorio-tipo")
    public ResponseEntity<List<RecordatoriosPorTipoDto>> ListaRecordatoriosPorTipo(
            @RequestParam("tipoRecordatorioId") Long tipoRecordatorioId
    ) {
        return ResponseEntity.ok(recordatoriosService.recordatoriosPorTipo(tipoRecordatorioId));
    }
    //Mostrar recordatorios por mascota
    @GetMapping("/recordatorio-mascota")
    public ResponseEntity<List<RecordatorioPorMascotaDto>> ListaRecordatoriosPorMascota(
            @RequestParam("mascotaId") Long mascotaId
    ){
        return ResponseEntity.ok(recordatoriosService.recordatoriosPorMascota(mascotaId));
    }

    //Mostrar recordatorios completados
    @GetMapping("/recordatorios-completados")
    public ResponseEntity<List<RecordatoriosCompletadosDTO>> obtenerRecordatoriosCompletados() {
        List<RecordatoriosCompletadosDTO> recordatoriosCompletados = recordatoriosService.recordatoriosCompletados();
        return ResponseEntity.ok(recordatoriosCompletados);
    }

    //Contar recordatorios por mascota
    @GetMapping("/contar-recordatorio-mascota")
    public ResponseEntity<List<ContarRecordatoriosxMascotaDto>> ListaCuentaRecordatoriosPorMascota(
            @RequestParam("mascotaId") Long mascotaId
    ){
        return ResponseEntity.ok(recordatoriosService.contarrecordatoriosPorMascota(mascotaId));
    }

    @PutMapping("/modificar-recordatorios/{id}")
    public void updateRecordatorios(@RequestBody RecordatoriosDto dto) {
        ModelMapper m = new ModelMapper();
        Recordatorios recordatorios = m.map(dto, Recordatorios.class);
        recordatoriosService.insert(recordatorios);
    }

    @DeleteMapping("/eliminar-recordatorio/{id}")
    public void delete(@PathVariable("id") Long id){
        recordatoriosService.delete(id);
    }

}
