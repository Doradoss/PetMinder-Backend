package com.upc.petminder.repositories;

import com.upc.petminder.entities.HistorialMedico;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface HistorialMedicoRepository extends JpaRepository<HistorialMedico, Long> {

    //Mostrar historial medico entre periodo de fechas
    @Query(value = "SELECT h.descripcion as descripcion, h.diagnostico as diagnostico, h.tratamiento as tratamiento, h.fecha as fecha " +
            "FROM historial_medico h JOIN mascota m ON h.mascota_id = m.id " +
            "WHERE m.id = :mascotaId AND h.fecha BETWEEN :from AND :to", nativeQuery = true)
    List<Tuple> historialMedicoPorMascotaYFecha(@Param("mascotaId") Long mascotaId, @Param("from") LocalDate from, @Param("to") LocalDate to);
}
