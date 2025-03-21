package com.example.proyectotalentotech.services;

import com.example.proyectotalentotech.model.Emprendimiento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.proyectotalentotech.repository.EmprendimientoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EmprendimientoService {

    private final EmprendimientoRepository emprendimientoRepository;

    @Autowired
    public EmprendimientoService(EmprendimientoRepository emprendimientoRepository) {
        this.emprendimientoRepository = emprendimientoRepository;
    }

    public List<Emprendimiento> listarTodos() {
        return emprendimientoRepository.findAll();
    }

    public Optional<Emprendimiento> obtenerPorId(Integer id) {
        return emprendimientoRepository.findById(id);
    }

    public Emprendimiento guardar(Emprendimiento emprendimiento) {
        return emprendimientoRepository.save(emprendimiento);
    }

    public void eliminar(Integer id) {
        emprendimientoRepository.deleteById(id);
    }
}

