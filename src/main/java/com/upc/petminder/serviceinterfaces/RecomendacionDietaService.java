package com.upc.petminder.serviceinterfaces;

import com.upc.petminder.dtos.RecomendacionDietaDTO.DietaPorMascotaYFechaDto;
import com.upc.petminder.dtos.RecomendacionDietaDTO.RecomendacionDietaDto;
import com.upc.petminder.entities.*;
import com.upc.petminder.repositories.DietaRepository;
import com.upc.petminder.repositories.MascotaRepository;
import com.upc.petminder.repositories.RecomendacionDietaRepository;
import jakarta.persistence.Tuple;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class RecomendacionDietaService {
    final RecomendacionDietaRepository recomendacionDietaRepository;
    final DietaRepository dietaRepository;
    final MascotaRepository mascotaRepository;

    public RecomendacionDietaService(RecomendacionDietaRepository recomendacionDietaRepository, DietaRepository dietaRepository, MascotaRepository mascotaRepository) {
        this.recomendacionDietaRepository = recomendacionDietaRepository;
        this.dietaRepository = dietaRepository;
        this.mascotaRepository = mascotaRepository;
    }

    public RecomendacionDietaDto save(RecomendacionDietaDto recomendacionDietaDto) {
        ModelMapper modelMapper = new ModelMapper();
        RecomendacionDieta recomendacionDieta = modelMapper.map(recomendacionDietaDto, RecomendacionDieta.class);
        Dieta dieta = dietaRepository.findById(recomendacionDietaDto.getDieta_id())
                .orElseThrow(() -> new IllegalArgumentException("Dieta no encontrada"));
        Mascota mascota = mascotaRepository.findById(recomendacionDietaDto.getMascota_id())
                .orElseThrow(() -> new IllegalArgumentException("Mascota no encontrada"));

        recomendacionDieta.setDieta(dieta);
        recomendacionDieta.setMascota(mascota);

        recomendacionDieta = recomendacionDietaRepository.save(recomendacionDieta);

        modelMapper.map(recomendacionDieta, recomendacionDietaDto);
        recomendacionDietaDto.setDieta_id(recomendacionDieta.getDieta().getId());
        recomendacionDietaDto.setMascota_id(recomendacionDieta.getMascota().getId());

        return recomendacionDietaDto;
    }

    public List<DietaPorMascotaYFechaDto> dietaPorMascotaYFechas(Integer mascotaId, LocalDate fecha) {
        List<Tuple> tuplas = recomendacionDietaRepository.dietasPorMascotaYFecha(mascotaId, fecha);
        List<DietaPorMascotaYFechaDto> ListDietaMascotaPorFecha = new ArrayList<>();
        DietaPorMascotaYFechaDto DietaMascotaPorFecha;
        for (Tuple tuple : tuplas) {
            DietaMascotaPorFecha = new DietaPorMascotaYFechaDto(
                    tuple.get("nombreDieta", String.class),
                    tuple.get("indicaciones", String.class)
            );
            ListDietaMascotaPorFecha.add(DietaMascotaPorFecha);
        }
        return ListDietaMascotaPorFecha;
    }




}
