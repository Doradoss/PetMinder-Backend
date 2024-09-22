package com.upc.petminder.serviceinterfaces;

import com.upc.petminder.dtos.RecordatoriosDTO.*;
import com.upc.petminder.entities.*;
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
import java.util.stream.Collectors;

@Service
public class RecordatoriosService {

    private List<Recordatorios> recordatorios = new ArrayList<>();  // Nuestros datos
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

    //Listar los registros de Recordatorios existentes
    public List<RecordatoriosDto> findAll() {
        List<Recordatorios> recordatorios = recordatoriosRepository.findAll();
        ModelMapper modelMapper = new ModelMapper();
        List<RecordatoriosDto> recordatorioDtos = new ArrayList<>();

        for (Recordatorios recordatorio : recordatorios) {
            RecordatoriosDto recordatorioDto = modelMapper.map(recordatorio, RecordatoriosDto.class);

            // Obtener claves foráneas de Usuario, Mascota y TipoRecordatorio
            Users usuario = recordatorio.getUsers();
            Mascota mascota = recordatorio.getMascota();
            TipoRecordatorio tipoRecordatorio = recordatorio.getTipo_recordatorio();

            recordatorioDto.setUsuario_id(usuario.getId());
            recordatorioDto.setMascota_id(mascota.getId());
            recordatorioDto.setTipo_recordatorio_id(tipoRecordatorio.getId());

            recordatorioDtos.add(recordatorioDto);
        }

        return recordatorioDtos;
    }


    //Listar por id
    public RecordatoriosDto getRecordatoriosById(Long id) {
        Recordatorios recordatorios = recordatoriosRepository.findById(id).orElse(null);
        if (recordatorios == null) { return null;}

        ModelMapper modelMapper = new ModelMapper();
        RecordatoriosDto dto = modelMapper.map(recordatorios, RecordatoriosDto.class);
        dto.setUsuario_id(recordatorios.getUsers().getId());
        dto.setMascota_id(recordatorios.getMascota().getId());
        dto.setTipo_recordatorio_id(recordatorios.getTipo_recordatorio().getId());
        return dto;
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

    private Recordatorios convertToEntity(RecordatoriosDto dto) {
        ModelMapper modelMapper = new ModelMapper();
        Recordatorios recordatorios = modelMapper.map(dto, Recordatorios.class);

        // Asegúrate de establecer las relaciones necesarias, por ejemplo:
        recordatorios.setMascota(mascotaRepository.findById(dto.getMascota_id()).orElse(null));
        recordatorios.setTipo_recordatorio(tipoRecordatorioRepository.findById(dto.getTipo_recordatorio_id()).orElse(null));
        // Y también el usuario, si es necesario
        // recordatorios.setUsers(userRepository.findById(dto.getUsuario_id()).orElse(null));

        return recordatorios;
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


    //Mostrar los recordatorios completados
    public List<RecordatoriosCompletadosDTO> recordatoriosCompletados() {
        List<Tuple> tuples = recordatoriosRepository.RecordatoriosCompletados();
        return tuples.stream()
                .map(tuple -> new RecordatoriosCompletadosDTO(
                        tuple.get("id", Long.class),
                        tuple.get("titulo", String.class),
                        tuple.get("descripcion", String.class),
                        tuple.get("fecha", Date.class),
                        tuple.get("hora", Time.class)
                ))
                .collect(Collectors.toList());
    }
    //Contar los recordatorios por mascota
    public List<ContarRecordatoriosxMascotaDto> contarrecordatoriosPorMascota (Long mascotaId) {
        List<Tuple> tuples = recordatoriosRepository.contarrecordatorioPorMascota(mascotaId);
        List<ContarRecordatoriosxMascotaDto> listCuentaRecordatoriosPorMascota = new ArrayList<>();
        ContarRecordatoriosxMascotaDto cuentarecordatoriosMascota;

        for (Tuple tuple : tuples) {
            cuentarecordatoriosMascota = new ContarRecordatoriosxMascotaDto(
                    tuple.get("total_recordatorios", Long.class)
            );
            listCuentaRecordatoriosPorMascota.add(cuentarecordatoriosMascota);
        }
        return listCuentaRecordatoriosPorMascota;
    }

    //Modificar Recordatorio
    public RecordatoriosDto update(Long id, RecordatoriosDto recordatorioDto) {
        // Buscar el recordatorio existente
        Recordatorios existingRecordatorio = recordatoriosRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Recordatorio no encontrado"));

        // Mapear los nuevos datos del DTO al recordatorio existente
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(recordatorioDto, existingRecordatorio);

        // Obtener el tipo de recordatorio asociado al tipo_recordatorio_id del DTO
        TipoRecordatorio tipoRecordatorio = tipoRecordatorioRepository.findById(recordatorioDto.getTipo_recordatorio_id())
                .orElseThrow(() -> new IllegalArgumentException("Tipo de recordatorio no encontrado"));

        // Asignar el tipo de recordatorio al recordatorio
        existingRecordatorio.setTipo_recordatorio(tipoRecordatorio);

        // Obtener el usuario asociado al usuario_id del DTO
        Users usuario = usersRepository.findById(recordatorioDto.getUsuario_id())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        // Asignar el usuario al recordatorio
        existingRecordatorio.setUsers(usuario);

        // Obtener la mascota asociada a mascota_id del DTO
        Mascota mascota = mascotaRepository.findById(recordatorioDto.getMascota_id())
                .orElseThrow(() -> new IllegalArgumentException("Mascota no encontrada"));

        // Asignar la mascota al recordatorio
        existingRecordatorio.setMascota(mascota);

        // Guardar el recordatorio actualizado
        Recordatorios updatedRecordatorio = recordatoriosRepository.save(existingRecordatorio);

        // Mapear la entidad actualizada al DTO y devolverla
        RecordatoriosDto updatedRecordatorioDto = modelMapper.map(updatedRecordatorio, RecordatoriosDto.class);
        updatedRecordatorioDto.setTipo_recordatorio_id(tipoRecordatorio.getId());
        updatedRecordatorioDto.setUsuario_id(usuario.getId());
        updatedRecordatorioDto.setMascota_id(mascota.getId());
        return updatedRecordatorioDto;
    }

    //Eliminar Recordatorio
    public void delete(Long id) {
        // Buscar el recordatorio existente
        Recordatorios recordatorio = recordatoriosRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Recordatorio no encontrado"));

        // Eliminar el recordatorio
        recordatoriosRepository.delete(recordatorio);
    }
}
