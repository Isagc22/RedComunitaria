package com.example.proyectotalentotech.services;

import com.example.proyectotalentotech.model.ComentariosYCalificaciones;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.proyectotalentotech.repository.ComentariosYCalificacionesRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ComentariosYCalificacionesService {

    private final ComentariosYCalificacionesRepository repository;

    @Autowired
    public ComentariosYCalificacionesService(ComentariosYCalificacionesRepository repository) {
        this.repository = repository;
    }

    public List<ComentariosYCalificaciones> listarTodos() {
        return repository.findAll();
    }

    public Optional<ComentariosYCalificaciones> obtenerPorId(Integer id) {
        return repository.findById(id);
    }

    public Optional<ComentariosYCalificaciones> editarPorId(Integer id) {
        return repository.findById(id);
    }


    public ComentariosYCalificaciones guardar(ComentariosYCalificaciones entity) {
        return repository.save(entity);
    }

    public void eliminar(Integer id) {
        repository.deleteById(id);
    }
}