package com.example.proyectotalentotech.services;
import com.example.proyectotalentotech.model.HistorialEmprendimiento;
import com.example.proyectotalentotech.model.ProduccionConsumoEnergia;
import com.example.proyectotalentotech.repository.ProduccionConsumoEnergiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Servicio que gestiona las operaciones de negocio relacionadas con la producción y consumo de energía.
 */
@Service
public class ProduccionConsumoEnergiaServices {

    private final ProduccionConsumoEnergiaRepository produccionConsumoEnergiaRepository;

    @Autowired
    public ProduccionConsumoEnergiaServices(ProduccionConsumoEnergiaRepository produccionConsumoEnergiaRepository) {
        this.produccionConsumoEnergiaRepository = produccionConsumoEnergiaRepository;
    }

    /**
     * Guarda un nuevo registro de producción y consumo de energía
     * 
     * @param produccionConsumoEnergia Datos a guardar
     * @return El registro guardado con su ID asignado
     */
    public ProduccionConsumoEnergia guardar(ProduccionConsumoEnergia produccionConsumoEnergia) {
        return produccionConsumoEnergiaRepository.save(produccionConsumoEnergia);
    }

    /**
     * Obtiene todos los registros de producción y consumo de energía.
     * 
     * @return Lista de todos los registros
     */
    public List<ProduccionConsumoEnergia> obtenerTodos() {
        return produccionConsumoEnergiaRepository.findAll();
    }

    /**
     * Busca un registro por su ID
     * 
     * @param id ID del registro a buscar
     * @return Optional con el registro si existe
     */
    public Optional<ProduccionConsumoEnergia> obtenerPorId(Long id) {
        return produccionConsumoEnergiaRepository.findById(id);
    }

    /**
     * Obtiene registros para un emprendimiento específico
     * 
     * @param emprendimientoId ID del emprendimiento
     * @return Lista de registros del emprendimiento
     */
    public List<ProduccionConsumoEnergia> obtenerPorEmprendimiento(Long emprendimientoId) {
        return produccionConsumoEnergiaRepository.findByEmprendimientoId(emprendimientoId);
    }

    /**
     * Obtiene registros para un rango de fechas
     * 
     * @param fechaDesde Fecha de inicio
     * @param fechaHasta Fecha de fin
     * @return Lista de registros en el rango de fechas
     */
    public List<ProduccionConsumoEnergia> obtenerPorRangoFechas(LocalDate fechaDesde, LocalDate fechaHasta) {
        return produccionConsumoEnergiaRepository.findByFechaBetween(fechaDesde, fechaHasta);
    }

    /**
     * Obtiene registros para un emprendimiento específico y un rango de fechas
     * 
     * @param emprendimientoId ID del emprendimiento
     * @param fechaDesde Fecha de inicio
     * @param fechaHasta Fecha de fin
     * @return Lista de registros que coinciden con los criterios
     */
    public List<ProduccionConsumoEnergia> obtenerPorEmprendimientoYRangoFechas(
            Long emprendimientoId, LocalDate fechaDesde, LocalDate fechaHasta) {
        return produccionConsumoEnergiaRepository.findByEmprendimientoIdAndFechaBetween(
                emprendimientoId, fechaDesde, fechaHasta);
    }

    /**
     * Elimina un registro por su ID
     * 
     * @param id ID del registro a eliminar
     */
    public void eliminar(Long id) {
        produccionConsumoEnergiaRepository.deleteById(id);
    }
    
    /**
     * Obtiene un resumen de la producción y consumo para todos los emprendimientos
     * 
     * @return Resumen de producción y consumo
     */
    public List<Map<String, Object>> obtenerResumenProduccionConsumo() {
        return produccionConsumoEnergiaRepository.getResumenProduccionConsumo();
    }
}