package com.example.proyectotalentotech.repository;

import com.example.proyectotalentotech.model.HistorialEmprendimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistorialEmprendimientoRepository extends JpaRepository<HistorialEmprendimiento, Integer> {
}
