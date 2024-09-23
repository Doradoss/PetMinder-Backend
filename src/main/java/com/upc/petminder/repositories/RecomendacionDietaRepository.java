package com.upc.petminder.repositories;

import com.upc.petminder.entities.RecomendacionDieta;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface RecomendacionDietaRepository extends JpaRepository<RecomendacionDieta, Long> {

    //Lista las dietas que tiene una mascota y por una fecha de asignacion especifica
    @Query(value = "SELECT d.nombre as nombreDieta, d.indicaciones as indicaciones\n" +
            "FROM recomendacion_dieta rd \n" +
            "JOIN dieta d ON rd.dieta_id = d.id \n" +
            "JOIN mascota m ON rd.mascota_id = m.id\n" +
            "WHERE m.id = :mascotaId AND rd.fecha = :fechaAsignacion", nativeQuery = true)
    List<Tuple> dietasPorMascotaYFecha(@Param("mascotaId") int mascotaId, @Param("fechaAsignacion") LocalDate fechaAsignacion);

    //Buscar dietas por Id de la Mascota
    @Query(value = "SELECT d.nombre as nombreDieta, d.indicaciones as indicaciones " +
            "FROM recomendacion_dieta rd " +
            "JOIN dieta d ON rd.dieta_id = d.id " +
            "WHERE rd.mascota_id = :mascotaId", nativeQuery = true)
    List<Tuple> buscaDietaPorMascotaId(@Param("mascotaId") int mascotaId);

}
