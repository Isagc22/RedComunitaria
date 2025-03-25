package com.example.proyectotalentotech.services;
import com.example.proyectotalentotech.model.HistorialEmprendimiento;
import com.example.proyectotalentotech.model.ProduccionConsumoEnergia;
import com.example.proyectotalentotech.repository.ProduccionConsumoEnergiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
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

    public Optional<ProduccionConsumoEnergia> obtenerPorId(Integer id) {
        return repository.findById(id);
    }

    public ProduccionConsumoEnergia guardar(ProduccionConsumoEnergia produccionConsumoEnergia) {
        return repository.save(produccionConsumoEnergia);
    }
    public Optional<ProduccionConsumoEnergia> editarPorId(Integer id) {
        return produccionConsumoEnergiaRepository.findById(id);
    }


    public void eliminar(Integer id) {
        repository.deleteById(id);
    }
}