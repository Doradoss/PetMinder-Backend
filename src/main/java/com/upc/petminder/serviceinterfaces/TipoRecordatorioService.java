package com.upc.petminder.serviceinterfaces;

import com.upc.petminder.dtos.RecordatoriosDTO.RecordatoriosDto;
import com.upc.petminder.dtos.TipoRecordatorioDTO.TipoRecordatorioDto;
import com.upc.petminder.entities.Recordatorios;
import com.upc.petminder.entities.TipoRecordatorio;
import com.upc.petminder.repositories.RecordatoriosRepository;
import com.upc.petminder.repositories.TipoRecordatorioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class TipoRecordatorioService {
    final TipoRecordatorioRepository tipoRecordatorioRepository;

    public TipoRecordatorioService(TipoRecordatorioRepository tipoRecordatorioRepository) {
        this.tipoRecordatorioRepository = tipoRecordatorioRepository;
    }

    public TipoRecordatorioDto save(TipoRecordatorioDto tipoRecordatorioDto) {
        ModelMapper modelMapper = new ModelMapper();
        TipoRecordatorio tipoRecordatorio = modelMapper.map(tipoRecordatorioDto, TipoRecordatorio.class);
        tipoRecordatorio = tipoRecordatorioRepository.save(tipoRecordatorio);
        return modelMapper.map(tipoRecordatorio, TipoRecordatorioDto.class);
    }

}
