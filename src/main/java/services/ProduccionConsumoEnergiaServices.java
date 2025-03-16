package services;

import model.ProduccionConsumoEnergia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.ProduccionConsumoEnergiaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProduccionConsumoEnergiaServices {

    private final ProduccionConsumoEnergiaRepository repository;

    @Autowired
    public ProduccionConsumoEnergiaServices(ProduccionConsumoEnergiaRepository repository) {
        this.repository = repository;
    }

    public List<ProduccionConsumoEnergia> listarTodos() {
        return repository.findAll();
    }

    public Optional<ProduccionConsumoEnergia> obtenerPorId(Integer id) {
        return repository.findById(id);
    }

    public ProduccionConsumoEnergia guardar(ProduccionConsumoEnergia entity) {
        return repository.save(entity);
    }

    public void eliminar(Integer id) {
        repository.deleteById(id);
    }
}

