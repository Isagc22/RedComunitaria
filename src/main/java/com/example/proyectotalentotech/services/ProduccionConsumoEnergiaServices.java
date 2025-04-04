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

@Service
public class ProduccionConsumoEnergiaServices {

    private final ProduccionConsumoEnergiaRepository repository;
    private final ProduccionConsumoEnergiaRepository produccionConsumoEnergiaRepository;

    @Autowired
    public ProduccionConsumoEnergiaServices(ProduccionConsumoEnergiaRepository repository, ProduccionConsumoEnergiaRepository produccionConsumoEnergiaRepository) {
        this.repository = repository;
        this.produccionConsumoEnergiaRepository = produccionConsumoEnergiaRepository;
    }

    public List<ProduccionConsumoEnergia> listarTodos() {
        return repository.findAll();
    }

    public Optional<ProduccionConsumoEnergia> obtenerPorId(Long id) {
        return repository.findById(id);
    }

    public ProduccionConsumoEnergia guardar(ProduccionConsumoEnergia produccionConsumoEnergia) {
        return repository.save(produccionConsumoEnergia);
    }
    public Optional<ProduccionConsumoEnergia> editarPorId(Long id) {
        return produccionConsumoEnergiaRepository.findById(id);
    }


    public void eliminar(Long id) {
        repository.deleteById(id);
    }
    
    public List<Map<String, Object>> getResumenProduccionConsumo() {
        return produccionConsumoEnergiaRepository.getResumenProduccionConsumo();
    }

    // Obtener datos por emprendimiento y rango de fechas
    public List<ProduccionConsumoEnergia> obtenerPorEmprendimientoYRangoFechas(Long emprendimientoId, LocalDate fechaDesde, LocalDate fechaHasta) {
        return produccionConsumoEnergiaRepository.findByEmprendimientoIdAndFechaBetweenOrderByFechaAsc(
                emprendimientoId, fechaDesde, fechaHasta);
    }

    // Obtener datos de un emprendimiento
    public List<ProduccionConsumoEnergia> obtenerPorEmprendimiento(Long emprendimientoId) {
        return produccionConsumoEnergiaRepository.findByEmprendimientoIdOrderByFechaDesc(emprendimientoId);
    }
}