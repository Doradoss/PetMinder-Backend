package com.upc.petminder.repositories;

import com.upc.petminder.entities.Notificacion;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {

    //Listar notificaciones no leidas por usuario
    @Query(value = "SELECT n.titulo, n.mensaje, n.fecha_creacion\n" +
            "FROM notificacion n\n" +
            "JOIN users u ON n.usuario_id = u.id\n" +
            "WHERE u.id = :user_id\n" +
            "AND n.leido = FALSE", nativeQuery = true)
    List<Tuple> notificacionesnoleidasXusario(@Param("user_id") int user_id);
}
