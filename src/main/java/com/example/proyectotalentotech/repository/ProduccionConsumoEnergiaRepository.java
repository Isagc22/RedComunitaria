package com.example.proyectotalentotech.repository;

import com.example.proyectotalentotech.model.ProduccionConsumoEnergia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Repositorio para acceder a los datos de producción y consumo de energía.
 */
@Repository
public interface ProduccionConsumoEnergiaRepository extends JpaRepository<ProduccionConsumoEnergia, Long> {

    /**
     * Busca registros por ID de emprendimiento
     */
    List<ProduccionConsumoEnergia> findByEmprendimientoId(Long emprendimientoId);
    
    /**
     * Busca registros en un rango de fechas
     */
    List<ProduccionConsumoEnergia> findByFechaBetween(LocalDate fechaInicio, LocalDate fechaFin);
    
    /**
     * Busca registros por ID de emprendimiento y rango de fechas ordenados cronológicamente
     */
    List<ProduccionConsumoEnergia> findByEmprendimientoIdAndFechaBetween(
            Long emprendimientoId, LocalDate fechaInicio, LocalDate fechaFin);
    
    /**
     * Busca registros por ID de emprendimiento y rango de fechas ordenados cronológicamente
     */
    List<ProduccionConsumoEnergia> findByEmprendimientoIdAndFechaBetweenOrderByFechaAsc(
            Long emprendimientoId, LocalDate fechaInicio, LocalDate fechaFin);
    
    /**
     * Obtiene un resumen de producción y consumo para todos los emprendimientos.
     * Devuelve un mapa con los totales y promedios por emprendimiento.
     */
    @Query(value = "SELECT e.nombre as nombre_emprendimiento, " +
            "SUM(p.produccion_energia) as total_produccion, " +
            "SUM(p.consumo_energia) as total_consumo, " +
            "AVG(p.produccion_energia) as promedio_produccion, " +
            "AVG(p.consumo_energia) as promedio_consumo " +
            "FROM produccionconsumoenergia p " +
            "JOIN emprendimiento e ON p.idemprendimiento = e.idemprendimiento " +
            "GROUP BY e.idemprendimiento, e.nombre", 
            nativeQuery = true)
    List<Map<String, Object>> getResumenProduccionConsumo();
}
