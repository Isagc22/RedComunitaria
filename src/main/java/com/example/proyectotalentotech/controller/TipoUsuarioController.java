package com.example.proyectotalentotech.controller;

import com.example.proyectotalentotech.model.TipoUsuario;
import com.example.proyectotalentotech.services.TipoUsuarioServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tipoUsuarios")
public class TipoUsuarioController {

    private final TipoUsuarioServices tipoUsuarioServices;

    public TipoUsuarioController(TipoUsuarioServices tipoUsuarioServices) {
        this.tipoUsuarioServices = tipoUsuarioServices;
    }

    @PostMapping
    public ResponseEntity<TipoUsuario> crear(@RequestBody TipoUsuario tipoUsuario) {
        return ResponseEntity.ok(tipoUsuarioServices.guardar(tipoUsuario));
    }

    @GetMapping
    public ResponseEntity<List<TipoUsuario>> listarTodos() {
        return ResponseEntity.ok(tipoUsuarioServices.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoUsuario> obtenerPorId(@PathVariable Integer id) {
        Optional<TipoUsuario> tipoUsuario = tipoUsuarioServices.obtenerPorId(id);
        return tipoUsuario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        tipoUsuarioServices.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
