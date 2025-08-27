package com.uniminuto.clinica.repository;

import com.uniminuto.clinica.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author lmora
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    
}
