package com.example.proyectotalentotech.repository;

import com.example.proyectotalentotech.model.ProduccionConsumoEnergia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Repository
public interface ProduccionConsumoEnergiaRepository extends JpaRepository<ProduccionConsumoEnergia, Long> {
    
    // Consulta para obtener datos por emprendimiento y rango de fechas
    List<ProduccionConsumoEnergia> findByEmprendimientoIdAndFechaBetweenOrderByFechaAsc(
            Long emprendimientoId, LocalDate fechaDesde, LocalDate fechaHasta);
    
    // Consulta para obtener datos por emprendimiento ordenados por fecha
    List<ProduccionConsumoEnergia> findByEmprendimientoIdOrderByFechaDesc(Long emprendimientoId);
    
    // Consulta para obtener el resumen de producción y consumo
    @Query(value = "SELECT * FROM get_resumen_produccion_consumo()", nativeQuery = true)
    List<Map<String, Object>> getResumenProduccionConsumo();
}
