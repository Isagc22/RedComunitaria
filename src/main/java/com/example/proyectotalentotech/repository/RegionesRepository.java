package com.example.proyectotalentotech.repository;

import com.example.proyectotalentotech.model.Regiones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionesRepository extends JpaRepository<Regiones, Integer> {
}
