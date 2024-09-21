package com.upc.petminder.serviceinterfaces;

import com.upc.petminder.dtos.NotificacionDTO.NotificacionDto;
import com.upc.petminder.dtos.NotificacionDTO.NotificacionNoLeidaXusuarioDto;
import com.upc.petminder.dtos.RecomendacionDietaDTO.DietaPorMascotaDto;
import com.upc.petminder.entities.*;
import com.upc.petminder.repositories.NotificacionRepository;
import com.upc.petminder.repositories.UserRepository;
import jakarta.persistence.Tuple;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class NotificacionService {
    //Tablas con la que trabajo
    final NotificacionRepository notificacionRepository;
    final UserRepository userRepository;

    public NotificacionService(NotificacionRepository notificacionRepository, UserRepository userRepository) {
        this.notificacionRepository = notificacionRepository;
        this.userRepository = userRepository;
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
}
