package services;

import model.Regiones;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.RegionesRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RegionesService {

    private final RegionesRepository regionesRepository;

    @Autowired
    public RegionesService(RegionesRepository regionesRepository) {
        this.regionesRepository = regionesRepository;
    }

    public List<Regiones> listarTodos() {
        return regionesRepository.findAll();
    }

    public Optional<Regiones> obtenerPorId(Integer id) {
        return regionesRepository.findById(id);
    }

    public Regiones guardar(Regiones regiones) {
        return regionesRepository.save(regiones);
    }

    public void eliminar(Integer id) {
        regionesRepository.deleteById(id);
    }
}

