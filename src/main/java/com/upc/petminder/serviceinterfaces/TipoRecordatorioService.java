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

    //Modificar tipo recordatorio
    public TipoRecordatorioDto update(Long id, TipoRecordatorioDto tipoRecordatorioDto) {
        // Buscar el tipo de recordatorio existente
        TipoRecordatorio existingTipoRecordatorio = tipoRecordatorioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tipo de recordatorio no encontrado"));

        // Mapear los nuevos datos del DTO al tipo de recordatorio existente
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(tipoRecordatorioDto, existingTipoRecordatorio);

        // Guardar el tipo de recordatorio actualizado
        TipoRecordatorio updatedTipoRecordatorio = tipoRecordatorioRepository.save(existingTipoRecordatorio);

        // Mapear la entidad actualizada al DTO y devolverla
        return modelMapper.map(updatedTipoRecordatorio, TipoRecordatorioDto.class);
    }

    // Eliminar un tipo de recordatorio
    public void delete(Long id) {
        // Verificar si el tipo de recordatorio existe
        if (!tipoRecordatorioRepository.existsById(id)) {
            throw new IllegalArgumentException("Tipo de recordatorio no encontrado");
        }

        // Eliminar el tipo de recordatorio
        tipoRecordatorioRepository.deleteById(id);
    }
}
