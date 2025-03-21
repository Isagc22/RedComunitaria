package com.example.proyectotalentotech.repository;

import com.example.proyectotalentotech.model.Emprendimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmprendimientoRepository extends JpaRepository<Emprendimiento, Integer> {
}
