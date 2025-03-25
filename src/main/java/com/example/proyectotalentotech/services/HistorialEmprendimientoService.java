package com.example.proyectotalentotech.services;

import com.example.proyectotalentotech.model.Emprendimiento;
import com.example.proyectotalentotech.model.HistorialEmprendimiento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.proyectotalentotech.repository.HistorialEmprendimientoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class HistorialEmprendimientoService {

    private final HistorialEmprendimientoRepository repository;
    private final HistorialEmprendimientoRepository historialEmprendimientoRepository;

    @Autowired
    public HistorialEmprendimientoService(HistorialEmprendimientoRepository repository, HistorialEmprendimientoRepository historialEmprendimientoRepository) {
        this.repository = repository;
        this.historialEmprendimientoRepository = historialEmprendimientoRepository;
    }

    public List<HistorialEmprendimiento> listarTodos() {
        return repository.findAll();
    }

    public Optional<HistorialEmprendimiento> obtenerPorId(Integer id) {
        return repository.findById(id);
    }

    public HistorialEmprendimiento guardar(HistorialEmprendimiento entity) {
        return repository.save(entity);
    }

    public Optional<HistorialEmprendimiento> editarPorId(Integer id) {
        return historialEmprendimientoRepository.findById(id);
    }

    public void eliminar(Integer id) {
        repository.deleteById(id);
    }
}