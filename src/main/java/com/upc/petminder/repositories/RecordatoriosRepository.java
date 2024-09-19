package com.upc.petminder.repositories;

import com.upc.petminder.entities.Recordatorios;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface RecordatoriosRepository extends JpaRepository<Recordatorios, Long> {
    //Mostrar Recordatorios por periodo de fecha
    @Query(value = "SELECT re.titulo as titulo, re.descripcion as descripcion, re.fecha as fecha, re.hora as hora " +
            "FROM recordatorios re " +
            "WHERE re.fecha BETWEEN :from AND :to", nativeQuery = true)
    List<Tuple> recordatoriosPorPeriodo (@Param("from") LocalDate from, @Param("to") LocalDate to);

    //Mostrar Recordatorios por periodo de fecha y tipo de recordatorio, incluyendo el tipo en el select
    @Query(value = "SELECT re.titulo as titulo, re.descripcion as descripcion, re.fecha as fecha, re.hora as hora, tr.nombre as tipo_recordatorio " +
            "FROM recordatorios re JOIN tipo_recordatorio tr ON re.tipo_recordatorio_id = tr.id " +
            "WHERE re.tipo_recordatorio_id = :tipoRecordatorioId", nativeQuery = true)
    List<Tuple> recordatoriosPorTipo(@Param("tipoRecordatorioId") Long tipo_recordatorio_id);

    //Mostrar recordatorios por mascota
    @Query(value = "SELECT re.titulo as titulo, re.descripcion as descripcion, re.fecha as fecha, re.hora as hora, m.nombre as nombre_mascota " +
            "FROM recordatorios re JOIN mascota m ON re.mascota_id = m.id " +
            "WHERE m.id = :mascotaId", nativeQuery = true)
    List<Tuple> recordatorioPorMascota(@Param("mascotaId") Long mascota_id);

    //Mostrar los recordatorios completados
    @Query(value = "SELECT r.id as id, r.titulo as titulo, r.descripcion as descripcion, r.fecha as fecha, r.hora as hora " +
            "FROM recordatorios r WHERE r.completado = true", nativeQuery = true)
    List<Tuple> RecordatoriosCompletados();

}
