package com.example.proyectotalentotech.repository;

import com.example.proyectotalentotech.model.Emprendimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Repositorio para la entidad Emprendimiento.
 * <p>
 * Esta interfaz proporciona métodos para acceder y manipular datos de emprendimientos
 * en la base de datos, incluyendo operaciones CRUD heredadas de JpaRepository y
 * consultas personalizadas para análisis de datos por región.
 * </p>
 * 
 * @author Equipo RedComunitaria
 * @version 1.0
 * @since 2023-03-30
 */
@Repository
public interface EmprendimientoRepository extends JpaRepository<Emprendimiento, Integer> {
    
    /**
     * Obtiene un conteo de los emprendimientos agrupados por región.
     * <p>
     * Esta consulta nativa SQL une las tablas de emprendimientos y regiones para contar
     * cuántos emprendimientos hay en cada región.
     * </p>
     * 
     * @return Lista de mapas conteniendo el nombre de la región y la cantidad de emprendimientos en cada una
     */
    @Query(value = "SELECT r.nombre_region as nombre_region, COUNT(e.idemprendimiento) as cantidad " +
           "FROM emprendimiento e " +
           "JOIN regiones r ON e.idregiones = r.idregiones " +
           "GROUP BY r.nombre_region", nativeQuery = true)
    List<Map<String, Object>> getEmprendimientosPorRegion();
    
    /**
     * Calcula el porcentaje de emprendimientos por región respecto al total.
     * <p>
     * Esta consulta nativa SQL calcula qué porcentaje del total de emprendimientos
     * corresponde a cada región, redondeando el resultado a dos decimales.
     * </p>
     * 
     * @return Lista de mapas conteniendo el nombre de la región y el porcentaje de emprendimientos en cada una
     */
    @Query(value = "SELECT r.nombre_region as nombre_region, " +
           "ROUND((COUNT(e.idemprendimiento) * 100.0 / (SELECT COUNT(*) FROM emprendimiento)), 2) as porcentaje " +
           "FROM emprendimiento e " +
           "JOIN regiones r ON e.idregiones = r.idregiones " +
           "GROUP BY r.nombre_region", nativeQuery = true)
    List<Map<String, Object>> getPorcentajeEmprendimientosPorRegion();
    
    /**
     * Encuentra emprendimientos por ID de usuario.
     * <p>
     * Este método permite recuperar todos los emprendimientos que pertenecen a un usuario específico,
     * facilitando la gestión de emprendimientos por propietario.
     * </p>
     * 
     * @param idusuarios ID del usuario propietario de los emprendimientos
     * @return Lista de emprendimientos pertenecientes al usuario especificado
     */
    List<Emprendimiento> findByIdusuarios(Integer idusuarios);
}
