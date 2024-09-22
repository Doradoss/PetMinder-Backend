package com.upc.petminder.serviceinterfaces;

import com.upc.petminder.dtos.DietaDTO.DietaDto;
import com.upc.petminder.dtos.DietaDTO.DietaPorFechaCreacionDto;
import com.upc.petminder.entities.Dieta;
import com.upc.petminder.repositories.DietaRepository;
import jakarta.persistence.Tuple;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
public class DietaService {

    private List<Dieta> dietas = new ArrayList<>();  // Nuestros datos
    final DietaRepository dietaRepository;

    public DietaService(DietaRepository dietaRepository) {
        this.dietaRepository = dietaRepository;
    }

    //Lista todos los registros existentes de Dieta
    public List<DietaDto> findAll() {
        List<Dieta> dietas = dietaRepository.findAll();
        ModelMapper modelMapper = new ModelMapper();
        List<DietaDto> dietaDtos = new ArrayList<>();

        for (Dieta dieta : dietas) {
            DietaDto dietaDto = modelMapper.map(dieta, DietaDto.class);
            dietaDtos.add(dietaDto);
        }

        return dietaDtos;
    }

    //Inserta Dieta
    public DietaDto save(DietaDto dietaDto) {
        ModelMapper modelMapper = new ModelMapper();
        Dieta dieta = modelMapper.map(dietaDto, Dieta.class);
        dieta= dietaRepository.save(dieta);
        return modelMapper.map(dieta, DietaDto.class);
    }


    public List<DietaPorFechaCreacionDto> dietaPorFechaCreacionDtos(LocalDate fecha) {
        List<Tuple> tuplas = dietaRepository.listDietaPorFechaCreacion(fecha);
        List<DietaPorFechaCreacionDto> ListDietaMascotaPorFecha = new ArrayList<>();
        DietaPorFechaCreacionDto DietaMascotaPorFecha;
        for (Tuple tuple : tuplas) {
            DietaMascotaPorFecha = new DietaPorFechaCreacionDto(
                    tuple.get("nombreDieta", String.class),
                    tuple.get("indicacionesDieta", String.class)
            );
            ListDietaMascotaPorFecha.add(DietaMascotaPorFecha);
        }
        return ListDietaMascotaPorFecha;
    }

    //Listar por id
    public DietaDto getDietaById(Long id) {
        Dieta dieta = dietaRepository.findById(id).orElse(null);
        if (dieta == null) { return null;}

        ModelMapper modelMapper = new ModelMapper();
        DietaDto dto = modelMapper.map(dieta, DietaDto.class);
        return dto;
    }

    // Modificar una dieta
    public DietaDto update(Long id, DietaDto dietaDto) {
        // Buscar la dieta existente
        Dieta existingDieta = dietaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Dieta no encontrada"));
        // Mapear los nuevos datos del DTO a la dieta existente
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(dietaDto, existingDieta);
        // Guardar la dieta actualizada
        Dieta updatedDieta = dietaRepository.save(existingDieta);
        // Mapear la entidad actualizada al DTO y devolverla
        return modelMapper.map(updatedDieta, DietaDto.class);
    }
    // Eliminar una dieta
    public void delete(Long id) {
        Dieta dieta = dietaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Dieta no encontrada"));
        // Eliminar la dieta
        dietaRepository.delete(dieta);
    }

}
