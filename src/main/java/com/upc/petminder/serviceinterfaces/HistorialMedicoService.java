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

    private List<HistorialMedico> historialMedicos = new ArrayList<>();
    final HistorialMedicoRepository historialMedicoRepository;
    final MascotaRepository mascotaRepository;

    public HistorialMedicoService(HistorialMedicoRepository historialMedicoRepository, MascotaRepository mascotaRepository) {
        this.historialMedicoRepository = historialMedicoRepository;
        this.mascotaRepository = mascotaRepository;
    }

    //Listar todos los registros de Historial Medico
    public List<HistorialMedicoDto> findAll() {
        List<HistorialMedico> historiales = historialMedicoRepository.findAll();
        ModelMapper modelMapper = new ModelMapper();
        List<HistorialMedicoDto> historialMedicoDtos = new ArrayList<>();

        for (HistorialMedico historialMedico : historiales) {
            HistorialMedicoDto historialMedicoDto = modelMapper.map(historialMedico, HistorialMedicoDto.class);

            // Obtener la clave foránea de Mascota y asignarla al DTO
            Mascota mascota = historialMedico.getMascota();
            historialMedicoDto.setMascota_id(mascota.getId());

            historialMedicoDtos.add(historialMedicoDto);
        }

        return historialMedicoDtos;
    }

    //Listar por id
    public HistorialMedicoDto getHistorialMedicoById(Long id) {
        HistorialMedico historialMedico = historialMedicoRepository.findById(id).orElse(null);
        if (historialMedico == null) { return null;}

        ModelMapper modelMapper = new ModelMapper();
        HistorialMedicoDto dto = modelMapper.map(historialMedico, HistorialMedicoDto.class);
        dto.setMascota_id(historialMedico.getMascota().getId());
        return dto;
    }

    //Inserta Historial Medico
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

    //actualizar HistorialMedico
    public HistorialMedicoDto updateHistorialMedico(Long id, HistorialMedicoDto historialMedicoDto) {
        // Buscar el historial médico existente
        HistorialMedico historialMedicoExistente = historialMedicoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Historial Médico no encontrado"));

        // Mapear los nuevos datos del DTO al historial existente
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(historialMedicoDto, historialMedicoExistente);

        // Obtener la mascota asociada al mascota_id del DTO
        Mascota mascota = mascotaRepository.findById(historialMedicoDto.getMascota_id())
                .orElseThrow(() -> new IllegalArgumentException("Mascota no encontrada"));

        // Asignar la mascota al historial médico
        historialMedicoExistente.setMascota(mascota);

        // Guardar el historial médico actualizado
        HistorialMedico historialMedicoActualizado = historialMedicoRepository.save(historialMedicoExistente);

        // Mapear la entidad actualizada al DTO y devolverla
        HistorialMedicoDto historialMedicoActualizadoDto = modelMapper.map(historialMedicoActualizado, HistorialMedicoDto.class);
        historialMedicoActualizadoDto.setMascota_id(mascota.getId());  // Asegurarse de incluir el ID de la mascota en el DTO
        return historialMedicoActualizadoDto;
    }

    //Eliminar Historial Medico
    public void delete(Long id) {
        historialMedicoRepository.deleteById(id);
    }

    //Listar Historial Medico Por Mascota Y Fecha
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
