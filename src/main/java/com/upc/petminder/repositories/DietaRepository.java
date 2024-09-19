package com.upc.petminder.repositories;

import com.upc.petminder.entities.Dieta;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface DietaRepository extends JpaRepository<Dieta, Long> {

    //Lista las dietas en base a una fecha especifica
    @Query(value="SELECT d.nombre AS nombreDieta, \n" +
            " d.indicaciones AS indicacionesDieta\n" +
            "FROM dieta d \n" +
            "WHERE d.fecha_creacion = :fecha\n" +
            "GROUP BY d.nombre, d.indicaciones;", nativeQuery = true)
    List<Tuple> listDietaPorFechaCreacion(@Param("fecha") LocalDate fecha);


}
