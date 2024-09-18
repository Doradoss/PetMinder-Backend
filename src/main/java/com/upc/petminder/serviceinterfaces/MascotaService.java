package com.upc.petminder.serviceinterfaces;

import com.upc.petminder.dtos.MascotaDTO.MascotaDto;
import com.upc.petminder.dtos.MascotaDTO.TotalMascotasPorEspecieDto;
import com.upc.petminder.entities.Mascota;
import com.upc.petminder.repositories.MascotaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


@Service
public class MascotaService {
    final MascotaRepository mascotaRepository;

    public MascotaService(MascotaRepository mascotaRepository) {
        this.mascotaRepository = mascotaRepository;
    }

    public MascotaDto save(MascotaDto mascotaDTO) {
        ModelMapper modelMapper = new ModelMapper();
        Mascota mascota = modelMapper.map(mascotaDTO, Mascota.class);
        mascota= mascotaRepository.save(mascota);
        return modelMapper.map(mascota, MascotaDto.class);
    }

    public TotalMascotasPorEspecieDto totalMascotasPorEspecieDto() {
        Long totalMascotaEspecie = mascotaRepository.TotalMascotasPorEspecie();
        TotalMascotasPorEspecieDto totalMascotaEspecieDTO = new TotalMascotasPorEspecieDto();
        totalMascotaEspecieDTO.setCantidad_mascotas(totalMascotaEspecie);
        return totalMascotaEspecieDTO;
    }

}
