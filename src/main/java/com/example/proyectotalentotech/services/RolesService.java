package com.example.proyectotalentotech.services;

import com.example.proyectotalentotech.model.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.proyectotalentotech.repository.RolesRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RolesService {

    private final RolesRepository rolRepository;

    @Autowired
    public RolesService(RolesRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    public List<Roles> listarTodos() {
        return rolRepository.findAll();
    }

    public Optional<Roles> obtenerPorId(Integer id) {
        return rolRepository.findById(id);
    }

    public Roles guardar(Roles rol) {
        return rolRepository.save(rol);
    }

    public void eliminar(Integer id) {
        rolRepository.deleteById(id);
    }
}