package com.upc.petminder.serviceinterfaces;

import com.upc.petminder.dtos.MascotaDTO.MascotaDto;
import com.upc.petminder.dtos.NotificacionDTO.NotificacionDto;
import com.upc.petminder.dtos.NotificacionDTO.NotificacionNoLeidaXusuarioDto;
import com.upc.petminder.entities.*;
import com.upc.petminder.repositories.NotificacionRepository;
import com.upc.petminder.repositories.UserRepository;
import jakarta.persistence.Tuple;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class NotificacionService {

    private List<Notificacion> notificacions = new ArrayList<>();  // Nuestros datos
    //Tablas con la que trabajo
    final NotificacionRepository notificacionRepository;
    final UserRepository userRepository;

    public NotificacionService(NotificacionRepository notificacionRepository, UserRepository userRepository) {
        this.notificacionRepository = notificacionRepository;
        this.userRepository = userRepository;
    }

    //Lista todos los registros de Notificaciones
    public List<NotificacionDto> findAll() {
        List<Notificacion> notificaciones = notificacionRepository.findAll();
        ModelMapper modelMapper = new ModelMapper();
        List<NotificacionDto> notificacionDtos = new ArrayList<>();

        for (Notificacion notificacion : notificaciones) {
            NotificacionDto notificacionDto = modelMapper.map(notificacion, NotificacionDto.class);

            // Obtener la clave foránea de Usuario y asignarla al DTO
            Users usuario = notificacion.getUsers();
            notificacionDto.setUsuario_id(usuario.getId());

            notificacionDtos.add(notificacionDto);
        }

        return notificacionDtos;
    }

    //Listar por id
    public NotificacionDto getNotificacionById(Long id) {
        Notificacion notificacion = notificacionRepository.findById(id).orElse(null);
        if (notificacion == null) { return null;}

        ModelMapper modelMapper = new ModelMapper();
        NotificacionDto dto = modelMapper.map(notificacion, NotificacionDto.class);
        dto.setUsuario_id(notificacion.getUsers().getId());
        return dto;
    }


    public NotificacionDto save(NotificacionDto notificacionDto) {
        ModelMapper modelMapper = new ModelMapper();
        Notificacion notificacion = modelMapper.map(notificacionDto, Notificacion.class);

        Users users = userRepository.findById(notificacionDto.getUsuario_id())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        notificacion.setUsers(users);

        notificacion = notificacionRepository.save(notificacion);

        modelMapper.map(notificacion, notificacionDto);
        notificacionDto.setUsuario_id(notificacion.getUsers().getId());

        return notificacionDto;
    }

    private Notificacion convertToEntity(NotificacionDto dto) {
        ModelMapper modelMapper = new ModelMapper();
        Notificacion notificacion = modelMapper.map(dto, Notificacion.class);

        notificacion.setUsers(userRepository.findById(dto.getUsuario_id()).orElse(null));

        return notificacion;
    }

    //Dieta por IdMascota
    public List<NotificacionNoLeidaXusuarioDto> noLeidaXusuario(Integer user_id) {
        List<Tuple> results = notificacionRepository.notificacionesnoleidasXusario(user_id);
        List<NotificacionNoLeidaXusuarioDto> ListnoLeidoxUsuario = new ArrayList<>();
        NotificacionNoLeidaXusuarioDto noLeidaXusuario;
        for (Tuple tuple : results) {
            noLeidaXusuario = new NotificacionNoLeidaXusuarioDto(
                    tuple.get("titulo", String.class),
                    tuple.get("mensaje", String.class),
                    tuple.get("fecha_creacion", Date.class)

            );
            ListnoLeidoxUsuario.add(noLeidaXusuario);
        }
        return ListnoLeidoxUsuario;
    }

    //Modificar Notificacion
    public NotificacionDto update(Long id, NotificacionDto notificacionDto) {
        // Buscar la notificación existente
        Notificacion existingNotificacion = notificacionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Notificación no encontrada"));

        // Mapear los nuevos datos del DTO a la notificación existente
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(notificacionDto, existingNotificacion);

        // Asignar el usuario a la notificación
        Users usuario = userRepository.findById(notificacionDto.getUsuario_id())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        existingNotificacion.setUsers(usuario);

        // Guardar la notificación actualizada
        Notificacion updatedNotificacion = notificacionRepository.save(existingNotificacion);

        // Mapear la entidad actualizada al DTO y devolverla
        NotificacionDto updatedNotificacionDto = modelMapper.map(updatedNotificacion, NotificacionDto.class);
        updatedNotificacionDto.setUsuario_id(usuario.getId());
        return updatedNotificacionDto;
    }

    // Eliminar una notificación por ID
    public void delete(Long id) {
        Notificacion notificacion = notificacionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Notificación no encontrada"));

        // Eliminar la notificación
        notificacionRepository.delete(notificacion);
    }
}
