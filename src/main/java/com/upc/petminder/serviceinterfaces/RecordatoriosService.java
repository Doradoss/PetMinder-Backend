package com.upc.petminder.serviceinterfaces;

import com.upc.petminder.dtos.RecordatoriosDTO.RecordatorioPorMascotaDto;
import com.upc.petminder.dtos.RecordatoriosDTO.RecordatoriosDto;
import com.upc.petminder.dtos.RecordatoriosDTO.RecordatoriosPorPeriodoDeFechasDto;
import com.upc.petminder.dtos.RecordatoriosDTO.RecordatoriosPorTipoDto;
import com.upc.petminder.entities.Mascota;
import com.upc.petminder.entities.Recordatorios;
import com.upc.petminder.entities.TipoRecordatorio;
import com.upc.petminder.entities.Users;
import com.upc.petminder.repositories.MascotaRepository;
import com.upc.petminder.repositories.RecordatoriosRepository;
import com.upc.petminder.repositories.TipoRecordatorioRepository;
import com.upc.petminder.repositories.UserRepository;
import jakarta.persistence.Tuple;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RecordatoriosService {
    final RecordatoriosRepository recordatoriosRepository;
    final TipoRecordatorioRepository tipoRecordatorioRepository;
    final UserRepository usersRepository;
    final MascotaRepository mascotaRepository;

    public RecordatoriosService(RecordatoriosRepository recordatoriosRepository,
                                TipoRecordatorioRepository tipoRecordatorioRepository,
                                UserRepository usersRepository,
                                MascotaRepository mascotaRepository) {
        this.recordatoriosRepository = recordatoriosRepository;
        this.tipoRecordatorioRepository = tipoRecordatorioRepository;
        this.usersRepository = usersRepository;
        this.mascotaRepository = mascotaRepository;
    }

    public RecordatoriosDto save(RecordatoriosDto recordatoriosDto) {
        ModelMapper modelMapper = new ModelMapper();

        // Convertir DTO a entidad Recordatorios
        Recordatorios recordatorios = modelMapper.map(recordatoriosDto, Recordatorios.class);

        // Buscar las entidades relacionadas por sus IDs y asignarlas
        TipoRecordatorio tipoRecordatorio = tipoRecordatorioRepository.findById(recordatoriosDto.getTipo_recordatorio_id())
                .orElseThrow(() -> new IllegalArgumentException("TipoRecordatorio no encontrado"));
        Users usuario = usersRepository.findById(recordatoriosDto.getUsuario_id())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        Mascota mascota = mascotaRepository.findById(recordatoriosDto.getMascota_id())
                .orElseThrow(() -> new IllegalArgumentException("Mascota no encontrada"));

        // Asignar las entidades relacionadas al objeto Recordatorios
        recordatorios.setTipo_recordatorio(tipoRecordatorio);
        recordatorios.setUsers(usuario);
        recordatorios.setMascota(mascota);

        // Guardar el recordatorio en la base de datos
        recordatorios = recordatoriosRepository.save(recordatorios);

        // Actualizar el DTO con la entidad guardada
        modelMapper.map(recordatorios, recordatoriosDto);
        recordatoriosDto.setTipo_recordatorio_id(recordatorios.getTipo_recordatorio().getId());
        recordatoriosDto.setUsuario_id(recordatorios.getUsers().getId());
        recordatoriosDto.setMascota_id(recordatorios.getMascota().getId());

        return recordatoriosDto;
    }

    public List<RecordatoriosPorPeriodoDeFechasDto> recordatoriosPorPeriodo (LocalDate from, LocalDate to){
        List<Tuple> tuples = recordatoriosRepository.recordatoriosPorPeriodo(from, to);
        List<RecordatoriosPorPeriodoDeFechasDto> ListRecordatoriosEnPeriodo = new ArrayList<>();
        RecordatoriosPorPeriodoDeFechasDto recordatoriosPeriodo;
        for (Tuple tuple : tuples) {
            recordatoriosPeriodo = new RecordatoriosPorPeriodoDeFechasDto(
                    tuple.get("titulo", String.class),
                    tuple.get("descripcion", String.class),
                    tuple.get("fecha", Date.class),
                    tuple.get("hora", Time.class)
            );
            ListRecordatoriosEnPeriodo.add(recordatoriosPeriodo);
        }
        return ListRecordatoriosEnPeriodo;
    }
    public List<RecordatoriosPorTipoDto> recordatoriosPorTipo (Long tipoRecordatorioId) {
        List<Tuple> tuples = recordatoriosRepository.recordatoriosPorTipo(tipoRecordatorioId);
        List<RecordatoriosPorTipoDto> listRecordatoriosPorTipo = new ArrayList<>();
        RecordatoriosPorTipoDto recordatoriosTipo;

        for (Tuple tuple : tuples) {
            recordatoriosTipo = new RecordatoriosPorTipoDto(
                    tuple.get("titulo", String.class),
                    tuple.get("descripcion", String.class),
                    tuple.get("fecha", Date.class),
                    tuple.get("hora", Time.class),
                    tuple.get("tipo_recordatorio", String.class)  // Incluimos el tipo de recordatorio
            );
            listRecordatoriosPorTipo.add(recordatoriosTipo);
        }
        return listRecordatoriosPorTipo;
    }

    public List<RecordatorioPorMascotaDto> recordatoriosPorMascota (Long mascotaId) {
        List<Tuple> tuples = recordatoriosRepository.recordatorioPorMascota(mascotaId);
        List<RecordatorioPorMascotaDto> listRecordatoriosPorMascota = new ArrayList<>();
        RecordatorioPorMascotaDto recordatoriosMascota;

        for (Tuple tuple : tuples) {
            recordatoriosMascota = new RecordatorioPorMascotaDto(
                    tuple.get("titulo", String.class),
                    tuple.get("descripcion", String.class),
                    tuple.get("fecha", Date.class),
                    tuple.get("hora", Time.class)
            );
            listRecordatoriosPorMascota.add(recordatoriosMascota);
        }
        return listRecordatoriosPorMascota;
    }
}
