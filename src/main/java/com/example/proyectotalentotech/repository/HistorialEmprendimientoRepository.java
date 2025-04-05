package com.example.proyectotalentotech.repository;

import com.example.proyectotalentotech.model.HistorialEmprendimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface HistorialEmprendimientoRepository extends JpaRepository<HistorialEmprendimiento, Integer> {
    
    @Query(value = "SELECT pais as nombrePais, SUM(CAST(cantidad_emprendimiento AS DECIMAL)) as cantidadEmprendimientos " +
           "FROM historialemprendimiento " +
           "GROUP BY pais " +
           "ORDER BY cantidadEmprendimientos DESC " +
           "LIMIT 10", nativeQuery = true)
    List<Map<String, Object>> getTopPaisesEmprendimiento();
}
