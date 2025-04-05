package com.example.proyectotalentotech.repository;

import com.example.proyectotalentotech.model.ProduccionConsumoEnergia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Repositorio para la entidad ProduccionConsumoEnergia.
 * <p>
 * Esta interfaz proporciona métodos para acceder y manipular datos de producción y consumo
 * de energía en la base de datos, incluyendo operaciones CRUD heredadas de JpaRepository y
 * consultas especializadas para análisis de datos energéticos por emprendimiento y fechas.
 * </p>
 * 
 * @author Equipo RedComunitaria
 * @version 1.0
 * @since 2023-03-30
 */
@Repository
public interface ProduccionConsumoEnergiaRepository extends JpaRepository<ProduccionConsumoEnergia, Long> {
    
    /**
     * Busca registros de producción y consumo de energía para un emprendimiento específico
     * dentro de un rango de fechas, ordenados ascendentemente por fecha.
     * <p>
     * Este método permite filtrar los datos energéticos por emprendimiento y período de tiempo,
     * facilitando el análisis temporal del comportamiento energético de un emprendimiento.
     * </p>
     * 
     * @param emprendimientoId Identificador del emprendimiento
     * @param fechaDesde Fecha de inicio del rango de búsqueda (inclusive)
     * @param fechaHasta Fecha de fin del rango de búsqueda (inclusive)
     * @return Lista de registros de producción y consumo que cumplen los criterios, ordenados por fecha ascendente
     */
    List<ProduccionConsumoEnergia> findByEmprendimientoIdAndFechaBetweenOrderByFechaAsc(
            Long emprendimientoId, LocalDate fechaDesde, LocalDate fechaHasta);
    
    /**
     * Busca todos los registros de producción y consumo de energía para un emprendimiento específico,
     * ordenados descendentemente por fecha (más recientes primero).
     * <p>
     * Este método permite obtener el historial completo de datos energéticos de un emprendimiento,
     * con los registros más recientes al principio de la lista.
     * </p>
     * 
     * @param emprendimientoId Identificador del emprendimiento
     * @return Lista de registros de producción y consumo del emprendimiento, ordenados por fecha descendente
     */
    List<ProduccionConsumoEnergia> findByEmprendimientoIdOrderByFechaDesc(Long emprendimientoId);
    
    /**
     * Obtiene un resumen estadístico de producción y consumo de energía para todos los emprendimientos.
     * <p>
     * Esta consulta utiliza una función almacenada en la base de datos que procesa y devuelve
     * información resumida sobre los patrones de producción y consumo energético.
     * </p>
     * 
     * @return Lista de mapas conteniendo el resumen de producción y consumo de energía
     */
    @Query(value = "SELECT * FROM get_resumen_produccion_consumo()", nativeQuery = true)
    List<Map<String, Object>> getResumenProduccionConsumo();
}
