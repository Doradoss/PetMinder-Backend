package com.upc.petminder.serviceinterfaces;

import com.upc.petminder.dtos.HistorialMedicoDTO.HistorialMedicoDto;
import com.upc.petminder.dtos.MascotaDTO.MascotaDto;
import com.upc.petminder.dtos.MascotaDTO.TotalMascotasPorEspecieDto;
import com.upc.petminder.entities.HistorialMedico;
import com.upc.petminder.entities.Mascota;
import com.upc.petminder.entities.Users;
import com.upc.petminder.repositories.MascotaRepository;
import com.upc.petminder.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class MascotaService {
    final MascotaRepository mascotaRepository;
    final UserRepository userRepository;
    private List<Mascota> mascotas = new ArrayList<>();

    public MascotaService(MascotaRepository mascotaRepository, UserRepository userRepository) {
        this.mascotaRepository = mascotaRepository;
        this.userRepository = userRepository;
    }

    //Lista todos los registros existentes de mascotas
    public List<MascotaDto> findAll() {
        List<Mascota> mascotas = mascotaRepository.findAll();
        ModelMapper modelMapper = new ModelMapper();
        List<MascotaDto> mascotaDtos = new ArrayList<>();

        for (Mascota mascota : mascotas) {
            MascotaDto mascotaDto = modelMapper.map(mascota, MascotaDto.class);

            // Obtener la clave foránea de Usuario y asignarla al DTO
            Users usuario = mascota.getUsers();
            mascotaDto.setUsuario_id(usuario.getId());

            mascotaDtos.add(mascotaDto);
        }

        return mascotaDtos;
    }

    public MascotaDto save(MascotaDto mascotaDTO) {
        ModelMapper modelMapper = new ModelMapper();
        Mascota mascota = modelMapper.map(mascotaDTO, Mascota.class);

        Users usuario = userRepository.findById(mascotaDTO.getUsuario_id())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        mascota.setUsers(usuario);

        mascota= mascotaRepository.save(mascota);

        modelMapper.map(mascota, mascotaDTO);
        mascotaDTO.setUsuario_id(mascota.getUsers().getId());
        return mascotaDTO;
    }

    public TotalMascotasPorEspecieDto totalMascotasPorEspecieDto() {
        Long totalMascotaEspecie = mascotaRepository.TotalMascotasPorEspecie();
        TotalMascotasPorEspecieDto totalMascotaEspecieDTO = new TotalMascotasPorEspecieDto();
        totalMascotaEspecieDTO.setCantidad_mascotas_especie(totalMascotaEspecie);
        return totalMascotaEspecieDTO;
    }

    //Listar por id
    public MascotaDto getMascotaById(Long id) {
        Mascota mascota = mascotaRepository.findById(id).orElse(null);
        if (mascota == null) { return null;}

        ModelMapper modelMapper = new ModelMapper();
        MascotaDto dto = modelMapper.map(mascota, MascotaDto.class);
        dto.setUsuario_id(mascota.getUsers().getId());
        return dto;
    }

}
