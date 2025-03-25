package com.example.proyectotalentotech.services;

import com.example.proyectotalentotech.model.ProduccionConsumoEnergia;
import com.example.proyectotalentotech.model.Regiones;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.proyectotalentotech.repository.RegionesRepository;

import javax.swing.plaf.synth.Region;
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

    public Optional<Regiones> editarPorId(Integer id) {
        return regionesRepository.findById(id);
    }

    public void eliminar(Integer id) {
        regionesRepository.deleteById(id);
    }
}
