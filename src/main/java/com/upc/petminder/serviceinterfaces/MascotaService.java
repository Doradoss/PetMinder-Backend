package com.upc.petminder.serviceinterfaces;

import com.upc.petminder.dtos.MascotaDTO.MascotaDto;
import com.upc.petminder.dtos.MascotaDTO.TotalMascotasPorEspecieDto;
import com.upc.petminder.entities.Mascota;
import com.upc.petminder.entities.Users;
import com.upc.petminder.repositories.MascotaRepository;
import com.upc.petminder.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


@Service
public class MascotaService {
    final MascotaRepository mascotaRepository;
    final UserRepository userRepository;

    public MascotaService(MascotaRepository mascotaRepository, UserRepository userRepository) {
        this.mascotaRepository = mascotaRepository;
        this.userRepository = userRepository;
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
        totalMascotaEspecieDTO.setCantidad_mascotas(totalMascotaEspecie);
        return totalMascotaEspecieDTO;
    }

}
