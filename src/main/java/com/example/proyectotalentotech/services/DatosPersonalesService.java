package com.example.proyectotalentotech.services;

import com.example.proyectotalentotech.model.DatosPersonales;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.proyectotalentotech.repository.DatosPersonalesRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DatosPersonalesService {

    private final DatosPersonalesRepository datosPersonalesRepository;

    @Autowired
    public DatosPersonalesService(DatosPersonalesRepository datosPersonalesRepository) {
        this.datosPersonalesRepository = datosPersonalesRepository;
    }

    public List<DatosPersonales> listarTodos() {
        return datosPersonalesRepository.findAll();
    }

    public Optional<DatosPersonales> obtenerPorId(Integer id) {
        return datosPersonalesRepository.findById(id);
    }

    public Optional<DatosPersonales> editarPorId(Integer id) {
        return datosPersonalesRepository.findById(id);
    }

    public DatosPersonales guardar(DatosPersonales datosPersonales) {
        return datosPersonalesRepository.save(datosPersonales);
    }

    public void eliminar(Integer id) {
        datosPersonalesRepository.deleteById(id);
    }


}
