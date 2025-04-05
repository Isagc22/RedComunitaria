package com.example.proyectotalentotech.repository;

import com.example.proyectotalentotech.model.Emprendimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface EmprendimientoRepository extends JpaRepository<Emprendimiento, Integer> {
    
    @Query(value = "SELECT r.nombre_region as nombreRegion, COUNT(e.idemprendimiento) as cantidadEmprendimientos " +
           "FROM emprendimiento e JOIN regiones r ON e.idregiones = r.idregiones " +
           "GROUP BY r.nombre_region", nativeQuery = true)
    List<Map<String, Object>> getEmprendimientosPorRegion();
    
    @Query(value = "SELECT r.nombre_region as nombreRegion, " +
           "ROUND((COUNT(e.idemprendimiento) * 100.0 / (SELECT COUNT(*) FROM emprendimiento)), 2) as porcentaje " +
           "FROM emprendimiento e JOIN regiones r ON e.idregiones = r.idregiones " +
           "GROUP BY r.nombre_region", nativeQuery = true)
    List<Map<String, Object>> getPorcentajeEmprendimientosPorRegion();
}
