package com.example.proyectotalentotech.repository;

import com.example.proyectotalentotech.model.HistorialEmprendimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Repositorio para la entidad HistorialEmprendimiento.
 * <p>
 * Esta interfaz proporciona métodos para acceder y manipular datos del historial
 * de emprendimientos en la base de datos, incluyendo operaciones CRUD heredadas
 * de JpaRepository y consultas personalizadas para análisis estadístico.
 * </p>
 * <p>
 * El historial de emprendimientos permite mantener registros históricos sobre los
 * emprendimientos, incluyendo información sobre su evolución, países de origen,
 * cantidades y otros datos relevantes para análisis.
 * </p>
 * 
 * @author Equipo RedComunitaria
 * @version 1.0
 * @since 2023-03-30
 */
@Repository
public interface HistorialEmprendimientoRepository extends JpaRepository<HistorialEmprendimiento, Integer> {
    
    /**
     * Obtiene los 10 países con mayor cantidad de emprendimientos registrados.
     * <p>
     * Esta consulta nativa SQL suma la cantidad de emprendimientos por país y devuelve
     * los 10 países con mayor número, ordenados de mayor a menor cantidad.
     * </p>
     * <p>
     * El resultado incluye el nombre del país y la suma total de emprendimientos en ese país.
     * </p>
     * 
     * @return Lista de mapas conteniendo el nombre del país y la cantidad total de emprendimientos
     */
    @Query(value = "SELECT pais as nombrePais, SUM(CAST(cantidad_emprendimiento AS DECIMAL)) as cantidadEmprendimientos " +
           "FROM historialemprendimiento " +
           "GROUP BY pais " +
           "ORDER BY cantidadEmprendimientos DESC " +
           "LIMIT 10", nativeQuery = true)
    List<Map<String, Object>> getTopPaisesEmprendimiento();
}
