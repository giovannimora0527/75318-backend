package com.uniminuto.clinica.repository;

import com.uniminuto.clinica.entity.TokenRecuperacion;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRecuperacionRepository extends JpaRepository<TokenRecuperacion, Long> {
    
    Optional<TokenRecuperacion> findByToken(String token);
    
    Optional<TokenRecuperacion> findByUsuarioIdAndUsadoFalse(Long usuarioId);
}