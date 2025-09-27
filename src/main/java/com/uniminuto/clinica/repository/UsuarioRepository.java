package com.uniminuto.clinica.repository;

import com.uniminuto.clinica.entity.Usuario;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author lmora
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    List<Usuario> findByRol(String rol);
    
    Optional<Usuario> findByUsername(String nombreUsuario);
<<<<<<< HEAD
    
        //Encontrar por ID usuario

    Optional<Usuario> findById(Long id);

=======
        
>>>>>>> 602c738be275f7f1826ccf9ef7bcb734aeea96d1
    List<Usuario> findByActivo(boolean estado);
}
