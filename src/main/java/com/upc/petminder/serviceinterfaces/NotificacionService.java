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
            notificacionDto.setUsuarioId(usuario.getId());

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
        dto.setUsuarioId(notificacion.getUsers().getId());
        return dto;
    }


    public NotificacionDto save(NotificacionDto notificacionDto) {
        ModelMapper modelMapper = new ModelMapper();
        Notificacion notificacion = modelMapper.map(notificacionDto, Notificacion.class);
        Users users = userRepository.findById(notificacionDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));


        notificacion.setUsers(users);

        notificacion = notificacionRepository.save(notificacion);

        modelMapper.map(notificacion, notificacionDto);
        notificacionDto.setUsuarioId(notificacion.getUsers().getId());

        return notificacionDto;
    }

    private Notificacion convertToEntity(NotificacionDto dto) {
        ModelMapper modelMapper = new ModelMapper();
        Notificacion notificacion = modelMapper.map(dto, Notificacion.class);

        notificacion.setUsers(userRepository.findById(dto.getUsuarioId()).orElse(null));

        return notificacion;
    }

    public void updateNotificacion(Long id, Notificacion notificacion) {

        NotificacionDto existingNotificacion = getNotificacionById(id);
        if (existingNotificacion != null) {
                        existingNotificacion.setTitulo(notificacion.getTitulo());
                        existingNotificacion.setMensaje(notificacion.getMensaje());
                        existingNotificacion.setLeido(notificacion.getLeido());
                        existingNotificacion.setFechaCreacion(notificacion.getFechaCreacion());
                        existingNotificacion.setUsuarioId(notificacion.getUsers().getId());

                        Notificacion updatedNotificacion = convertToEntity(existingNotificacion);
                        notificacionRepository.save(updatedNotificacion);
        };
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

    public void insert(Notificacion notificacion) {
        notificacionRepository.save(notificacion);
    }

    public void delete(Long id) {
        notificacionRepository.deleteById(id);
    }

}
