package com.uniminuto.clinica.repository;

import com.uniminuto.clinica.entity.ActualizaciondeUsuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActualizacionUsuarioRepository extends JpaRepository<ActualizaciondeUsuario, Long> {
    Optional<ActualizaciondeUsuario> findByUsername(String username);
}