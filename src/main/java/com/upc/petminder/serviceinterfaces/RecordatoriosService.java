package com.upc.petminder.serviceinterfaces;

import com.upc.petminder.dtos.RecordatoriosDto;
import com.upc.petminder.dtos.RecordatoriosPorPeriodoDeFechasDto;
import com.upc.petminder.entities.Recordatorios;
import com.upc.petminder.repositories.RecordatoriosRepository;
import jakarta.persistence.Tuple;
import org.modelmapper.ModelMapper;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RecordatoriosService {
    final RecordatoriosRepository recordatoriosRepository;

    public RecordatoriosService(RecordatoriosRepository recordatoriosRepository) {
        this.recordatoriosRepository = recordatoriosRepository;
    }

    public RecordatoriosDto save(RecordatoriosDto recordatoriosDto) {
        ModelMapper modelMapper = new ModelMapper();
        Recordatorios recordatorios = modelMapper.map(recordatoriosDto, Recordatorios.class);
        recordatorios = recordatoriosRepository.save(recordatorios);
        return modelMapper.map(recordatorios, RecordatoriosDto.class);
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
}
