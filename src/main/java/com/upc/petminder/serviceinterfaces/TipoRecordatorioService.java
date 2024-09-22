package com.upc.petminder.serviceinterfaces;


import com.upc.petminder.dtos.DietaDTO.DietaDto;
import com.upc.petminder.dtos.TipoRecordatorioDTO.TipoRecordatorioDto;
import com.upc.petminder.entities.Dieta;
import com.upc.petminder.entities.Recordatorios;
import com.upc.petminder.entities.TipoRecordatorio;
import com.upc.petminder.repositories.TipoRecordatorioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class TipoRecordatorioService {
    private List<TipoRecordatorio> tipoRecordatorios = new ArrayList<>();  //
    final TipoRecordatorioRepository tipoRecordatorioRepository;

    public TipoRecordatorioService(TipoRecordatorioRepository tipoRecordatorioRepository) {
        this.tipoRecordatorioRepository = tipoRecordatorioRepository;
    }

    //Lista todos los registros de Tipo Recordatorio
    public List<TipoRecordatorioDto> findAll() {
        List<TipoRecordatorio> tipos = tipoRecordatorioRepository.findAll();
        ModelMapper modelMapper = new ModelMapper();
        List<TipoRecordatorioDto> tipoDtos = new ArrayList<>();

        for (TipoRecordatorio tipo : tipos) {
            TipoRecordatorioDto tipoDto = modelMapper.map(tipo, TipoRecordatorioDto.class);
            tipoDtos.add(tipoDto);
        }

        return tipoDtos;
    }

    public TipoRecordatorioDto save(TipoRecordatorioDto tipoRecordatorioDto) {
        ModelMapper modelMapper = new ModelMapper();
        TipoRecordatorio tipoRecordatorio = modelMapper.map(tipoRecordatorioDto, TipoRecordatorio.class);
        tipoRecordatorio = tipoRecordatorioRepository.save(tipoRecordatorio);
        return modelMapper.map(tipoRecordatorio, TipoRecordatorioDto.class);
    }

    //Listar por id
    public TipoRecordatorioDto getTipoRecordatorioById(Long id) {
        TipoRecordatorio tipoRecordatorio = tipoRecordatorioRepository.findById(id).orElse(null);
        if (tipoRecordatorio == null) { return null;}

        ModelMapper modelMapper = new ModelMapper();
        TipoRecordatorioDto dto = modelMapper.map(tipoRecordatorio, TipoRecordatorioDto.class);
        return dto;
    }


}
