package com.example.proyectotalentotech.controller;

import com.example.proyectotalentotech.model.TipoUsuario;
import com.example.proyectotalentotech.services.TipoUsuarioServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tipousuario")
public class TipoUsuarioController {

    private final TipoUsuarioServices tipoUsuarioService;

    public TipoUsuarioController(TipoUsuarioServices tipoUsuarioService) {
        this.tipoUsuarioService = tipoUsuarioService;
    }

    @PostMapping
    public ResponseEntity<TipoUsuario> crear(@RequestBody TipoUsuario tipoUsuario) {
        return ResponseEntity.ok(tipoUsuarioService.guardar(tipoUsuario));
    }

    @GetMapping
    public ResponseEntity<List<TipoUsuario>> listarTodos() {
        List<TipoUsuario> lista = tipoUsuarioService.listarTodos();
        if (lista.isEmpty()) {
            System.out.println("⚠️ La tabla de tipo de usuario está vacía.");
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoUsuario> obtenerPorId(@PathVariable Integer id) {
        Optional<TipoUsuario> entity = tipoUsuarioService.obtenerPorId(id);
        if (entity.isEmpty()) {
            System.out.println("No se encontró el tipo de usuario con id " + id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(entity.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoUsuario> actualizar(@PathVariable Integer id, @RequestBody TipoUsuario tipoUsuario) {
        Optional<TipoUsuario> entity = tipoUsuarioService.editarPorId(id);

        if (entity.isPresent()) {
            TipoUsuario tipoUsuarioActual = entity.get();
            tipoUsuarioActual.setNombre_tipo_usuario(tipoUsuario.getNombre_tipo_usuario());
            tipoUsuarioActual.setEstado_tipo_usuario(tipoUsuario.isEstado_tipo_usuario());
            return ResponseEntity.ok(tipoUsuarioService.guardar(tipoUsuarioActual));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (!tipoUsuarioService.obtenerPorId(id).isPresent()) {
            System.out.println("No se encontró el tipo de usuario con id " + id);
            return ResponseEntity.badRequest().build();
        }
        tipoUsuarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}