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

    //Inserta Recomendacion Dieta
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

    //Listar las Recomendacion de dieta por fecha y un periodo de fechas
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

    // Modificar una recomendación de dieta
    public RecomendacionDietaDto update(Long id, RecomendacionDietaDto recomendacionDietaDto) {
        // Buscar la recomendación existente
        RecomendacionDieta existingRecomendacion = recomendacionDietaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Recomendación de dieta no encontrada"));

        // Actualizar los campos relevantes
        existingRecomendacion.setFecha(recomendacionDietaDto.getFecha());

        // Asignar la dieta a la recomendación
        Dieta dieta = dietaRepository.findById(recomendacionDietaDto.getDieta_id())
                .orElseThrow(() -> new IllegalArgumentException("Dieta no encontrada"));
        existingRecomendacion.setDieta(dieta);

        // Asignar la mascota a la recomendación
        Mascota mascota = mascotaRepository.findById(recomendacionDietaDto.getMascota_id())
                .orElseThrow(() -> new IllegalArgumentException("Mascota no encontrada"));
        existingRecomendacion.setMascota(mascota);

        // Guardar la recomendación de dieta actualizada
        RecomendacionDieta updatedRecomendacion = recomendacionDietaRepository.save(existingRecomendacion);

        // Mapear la entidad actualizada al DTO y devolverla
        RecomendacionDietaDto updatedRecomendacionDto = new RecomendacionDietaDto();
        updatedRecomendacionDto.setId(updatedRecomendacion.getId());
        updatedRecomendacionDto.setFecha(updatedRecomendacion.getFecha());
        updatedRecomendacionDto.setDieta_id(dieta.getId());
        updatedRecomendacionDto.setMascota_id(mascota.getId());

        return updatedRecomendacionDto;
    }

    // Eliminar una recomendación de dieta
    public void delete(Long id) {
        RecomendacionDieta recomendacion = recomendacionDietaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Recomendación de dieta no encontrada"));

        // Eliminar la recomendación
        recomendacionDietaRepository.delete(recomendacion);
    }

}
