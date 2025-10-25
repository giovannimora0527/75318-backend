package com.uniminuto.clinica.repository;

import com.uniminuto.clinica.entity.Medicamento;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicamentoRepository extends JpaRepository<Medicamento, Long> {
    
    List<Medicamento> findAllByOrderByNombreAsc();
    
    List<Medicamento> findAllByOrderByIdDesc();
    
    Optional<Medicamento> findByNombre(String nombre);
}