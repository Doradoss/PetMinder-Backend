package com.upc.petminder.serviceinterfaces;

import com.upc.petminder.dtos.HistorialMedicoDTO.HistorialMedicoDto;
import com.upc.petminder.dtos.HistorialMedicoDTO.HistorialMedicoPorMascotaYFechaDto;
import com.upc.petminder.entities.HistorialMedico;
import com.upc.petminder.entities.Mascota;
import com.upc.petminder.repositories.HistorialMedicoRepository;
import com.upc.petminder.repositories.MascotaRepository;
import jakarta.persistence.Tuple;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class HistorialMedicoService {
    final HistorialMedicoRepository historialMedicoRepository;
    final MascotaRepository mascotaRepository;

    public HistorialMedicoService(HistorialMedicoRepository historialMedicoRepository, MascotaRepository mascotaRepository) {
        this.historialMedicoRepository = historialMedicoRepository;
        this.mascotaRepository = mascotaRepository;
    }

    public HistorialMedicoDto save(HistorialMedicoDto historialMedicoDto) {
        ModelMapper modelMapper = new ModelMapper();
        HistorialMedico historialMedico = modelMapper.map(historialMedicoDto, HistorialMedico.class);
        Mascota mascota = mascotaRepository.findById(historialMedicoDto.getMascota_id())
                .orElseThrow(() -> new IllegalArgumentException("Mascota no encontrada"));

        historialMedico.setMascota(mascota);

        historialMedico = historialMedicoRepository.save(historialMedico);

        modelMapper.map(historialMedico, historialMedicoDto);
        historialMedicoDto.setMascota_id(historialMedico.getMascota().getId());

        return historialMedicoDto;
    }

    public List<HistorialMedicoPorMascotaYFechaDto> historialMedicoPorMascotaYFecha(Long mascotaId, LocalDate from, LocalDate to) {
        List<Tuple> tuples = historialMedicoRepository.historialMedicoPorMascotaYFecha(mascotaId, from, to);
        List<HistorialMedicoPorMascotaYFechaDto> ListHistorialMedicoPorMascotaYFecha = new ArrayList<>();
        HistorialMedicoPorMascotaYFechaDto HistorialMedicoPorMascotaYFecha;
        for (Tuple tuple : tuples) {
            HistorialMedicoPorMascotaYFecha = new HistorialMedicoPorMascotaYFechaDto(
                    tuple.get("descripcion", String.class),
                    tuple.get("diagnostico", String.class),
                    tuple.get("tratamiento", String.class),
                    tuple.get("fecha", Date.class)
            );
            ListHistorialMedicoPorMascotaYFecha.add(HistorialMedicoPorMascotaYFecha);
        }
        return ListHistorialMedicoPorMascotaYFecha;
    }
}
