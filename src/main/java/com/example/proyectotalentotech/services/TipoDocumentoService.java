package com.example.proyectotalentotech.services;


import com.example.proyectotalentotech.model.Roles;
import com.example.proyectotalentotech.model.TipoDocumento;
import com.example.proyectotalentotech.model.TipoUsuario;
import com.example.proyectotalentotech.repository.TipoDocumentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TipoDocumentoService {

    private final TipoDocumentoRepository tipoDocumentoRepository;

    @Autowired
    public TipoDocumentoService(TipoDocumentoRepository tipoDocumentoRepository) {
        this.tipoDocumentoRepository = tipoDocumentoRepository;
    }

    public TipoDocumento guardar(TipoDocumento tipoDocumento) {
        return tipoDocumentoRepository.save(tipoDocumento);
    }

    public List<TipoDocumento> listarTodos() {
        return tipoDocumentoRepository.findAll();
    }

    public Optional<TipoDocumento> obtenerPorId(Integer idTipoDocumento) {
        return tipoDocumentoRepository.findById(idTipoDocumento);
    }

    public Optional<TipoDocumento> editarPorId(Integer id) {
        return tipoDocumentoRepository.findById(id);
    }

    public void eliminar(Integer id) {
        tipoDocumentoRepository.deleteById(id);
    }
}