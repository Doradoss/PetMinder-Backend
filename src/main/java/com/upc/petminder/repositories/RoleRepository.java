package com.upc.petminder.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.upc.petminder.entities.Role;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}