package com.upc.petminder.serviceinterfaces;

import com.upc.petminder.dtos.RecomendacionDietaDTO.DietaPorMascotaDto;
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
import java.util.Optional;

@Service
public class RecomendacionDietaService {

    private List<RecomendacionDieta> recomendacionDietas = new ArrayList<>();
    final RecomendacionDietaRepository recomendacionDietaRepository;
    final DietaRepository dietaRepository;
    final MascotaRepository mascotaRepository;

    public RecomendacionDietaService(RecomendacionDietaRepository recomendacionDietaRepository, DietaRepository dietaRepository, MascotaRepository mascotaRepository) {
        this.recomendacionDietaRepository = recomendacionDietaRepository;
        this.dietaRepository = dietaRepository;
        this.mascotaRepository = mascotaRepository;
    }

    //Lista todos los registros de Recomendacion Dieta
    public List<RecomendacionDietaDto> findAll() {
        List<RecomendacionDieta> recomendaciones = recomendacionDietaRepository.findAll();
        ModelMapper modelMapper = new ModelMapper();
        List<RecomendacionDietaDto> recomendacionDietaDtos = new ArrayList<>();

        for (RecomendacionDieta recomendacionDieta : recomendaciones) {
            RecomendacionDietaDto recomendacionDietaDto = modelMapper.map(recomendacionDieta, RecomendacionDietaDto.class);

            // Obtener claves foráneas de Dieta y Mascota y asignarlas al DTO
            Dieta dieta = recomendacionDieta.getDieta();
            Mascota mascota = recomendacionDieta.getMascota();
            recomendacionDietaDto.setDieta_id(dieta.getId());
            recomendacionDietaDto.setMascota_id(mascota.getId());

            recomendacionDietaDtos.add(recomendacionDietaDto);
        }

        return recomendacionDietaDtos;
    }

    //Listar por id
    public RecomendacionDietaDto getRecomendacionDietaById(Long id) {
        RecomendacionDieta recomendacionDieta = recomendacionDietaRepository.findById(id).orElse(null);
        if (recomendacionDieta == null) { return null;}

        ModelMapper modelMapper = new ModelMapper();
        RecomendacionDietaDto dto = modelMapper.map(recomendacionDieta, RecomendacionDietaDto.class);
        dto.setDieta_id(recomendacionDieta.getDieta().getId());
        dto.setMascota_id(recomendacionDieta.getMascota().getId());
        return dto;
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
    private RecomendacionDieta convertToEntity(RecomendacionDietaDto dto) {
        ModelMapper modelMapper = new ModelMapper();
        RecomendacionDieta recomendacionDieta = modelMapper.map(dto, RecomendacionDieta.class);

        // Asignar la dieta, mascota y tipo de recordatorio
        recomendacionDieta.setDieta(dietaRepository.findById(dto.getDieta_id()).orElse(null));
        recomendacionDieta.setMascota(mascotaRepository.findById(dto.getMascota_id()).orElse(null));


        return recomendacionDieta;
    }

    public void updateRecomendacionDieta(Long id, RecomendacionDieta recomendacionDieta) {
        RecomendacionDietaDto existingRecomendacionDieta = getRecomendacionDietaById(id);
        if (existingRecomendacionDieta != null) {
            existingRecomendacionDieta.setFecha(recomendacionDieta.getFecha());
            existingRecomendacionDieta.setDieta_id(recomendacionDieta.getDieta().getId());
            existingRecomendacionDieta.setMascota_id(recomendacionDieta.getMascota().getId());

            RecomendacionDieta updatedRecomendacionDieta = convertToEntity(existingRecomendacionDieta);
            recomendacionDietaRepository.save(updatedRecomendacionDieta);
        }
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

    //Dieta por IdMascota
    public List<DietaPorMascotaDto> DietasPorMascota(Integer mascotaId) {
        List<Tuple> results = recomendacionDietaRepository.buscaDietaPorMascotaId(mascotaId);
        List<DietaPorMascotaDto> ListDietaPorMascota = new ArrayList<>();
        DietaPorMascotaDto DietaPorMascota;
        for (Tuple tuple : results) {
            DietaPorMascota = new DietaPorMascotaDto(
                    tuple.get("nombreDieta", String.class),
                    tuple.get("indicaciones", String.class)

            );
            ListDietaPorMascota.add(DietaPorMascota);
        }
        return ListDietaPorMascota;
    }

    public void insert(RecomendacionDieta recomendacionDieta) {
        recomendacionDietaRepository.save(recomendacionDieta);
    }

    public void delete(Long id) {
        recomendacionDietaRepository.deleteById(id);
    }
}
