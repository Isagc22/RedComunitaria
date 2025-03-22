package com.example.proyectotalentotech.controller;

import com.example.proyectotalentotech.model.TipoUsuario;
import com.example.proyectotalentotech.model.Usuario;
import com.example.proyectotalentotech.services.TipoUsuarioServices;
import com.example.proyectotalentotech.services.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tipoUsuarios")
public class TipoUsuarioController {
    private final TipoUsuarioServices tipoUsuarioService;

    public TipoUsuarioController(TipoUsuarioServices tipousuarioservice) {
        this.tipoUsuarioService = tipousuarioservice;
    }

    @PostMapping
    public ResponseEntity<TipoUsuario> crearTipoUsuario(@RequestBody TipoUsuario tipousuario) {
        return ResponseEntity.ok(tipoUsuarioService.guardar(tipousuario));

    }

    @GetMapping
    public ResponseEntity<List<TipoUsuario>> obtenerUsuarios() {
        return ResponseEntity.ok(tipoUsuarioService.listarTodos());
    }
}