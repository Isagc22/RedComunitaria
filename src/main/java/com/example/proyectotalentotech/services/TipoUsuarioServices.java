package com.example.proyectotalentotech.services;

import com.example.proyectotalentotech.model.TipoDocumento;
import com.example.proyectotalentotech.model.TipoUsuario;
import com.example.proyectotalentotech.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.proyectotalentotech.repository.TipoUsuarioRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TipoUsuarioServices {

    private final TipoUsuarioRepository tipoUsuarioRepository;

    @Autowired
    public TipoUsuarioServices(TipoUsuarioRepository tipoUsuarioRepository) {
        this.tipoUsuarioRepository = tipoUsuarioRepository;
    }


    public List<TipoUsuario> listarTodos() {
        return tipoUsuarioRepository.findAll();
    }

    public Optional<TipoUsuario> obtenerPorId(Integer id) {
        return tipoUsuarioRepository.findById(id);
    }

    public TipoUsuario guardar(TipoUsuario tipoUsuario) {
        return tipoUsuarioRepository.save(tipoUsuario);
    }

    public void eliminar(Integer id) {
        tipoUsuarioRepository.deleteById(id);
    }

    public Optional<TipoUsuario> editarPorId(Integer id) {
        return tipoUsuarioRepository.findById(id);
    }
}