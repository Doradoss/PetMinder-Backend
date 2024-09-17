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
    @Query(value = "SELECT re.titulo as titulo, re.descripcion as descripcion, re.fecha as fecha, re.hora as hora FROM recordatorios re WHERE re.fecha BETWEEN :from AND :to", nativeQuery = true)
    List<Tuple> recordatoriosPorPeriodo (@Param("from") LocalDate from, @Param("to") LocalDate to);
}
