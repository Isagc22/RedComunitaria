package com.example.proyectotalentotech.repository;

import com.example.proyectotalentotech.model.DatosPersonales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DatosPersonalesRepository extends JpaRepository<DatosPersonales, Integer> {
    Optional<DatosPersonales> findByUsuario_idUSUARIOS(Integer usuarioId);
}


