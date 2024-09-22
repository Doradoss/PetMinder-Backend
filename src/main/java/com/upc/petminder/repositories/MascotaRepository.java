package com.upc.petminder.repositories;

import com.upc.petminder.entities.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
//Muestra el total de mascotas por tipo de especie existente en el sistema
public interface MascotaRepository extends JpaRepository<Mascota, Long> {
    @Query(value="SELECT COUNT(DISTINCT especie) AS cantidad_mascotas_especie\n" +
            "            FROM mascota;", nativeQuery = true)
     Long TotalMascotasPorEspecie();
}
