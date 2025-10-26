package com.uniminuto.clinica.repository;

import com.uniminuto.clinica.entity.Historia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoriaRepository extends JpaRepository< Historia, Integer> {

}
