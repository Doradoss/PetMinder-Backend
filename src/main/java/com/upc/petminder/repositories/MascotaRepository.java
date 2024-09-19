package com.upc.petminder.repositories;

import com.upc.petminder.entities.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
//Muestra el total de mascotas por tipo de especie existente en el sistema
public interface MascotaRepository extends JpaRepository<Mascota, Long> {
    @Query(value="SELECT especie, COUNT(*) AS cantidad_mascotas\n" +
            "FROM mascota\n" +
            "GROUP BY especie;", nativeQuery = true)
    Long TotalMascotasPorEspecie();
}
