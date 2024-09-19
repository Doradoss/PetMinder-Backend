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
    final DietaRepository dietaRepository;

    public DietaService(DietaRepository dietaRepository) {
        this.dietaRepository = dietaRepository;
    }

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



}
