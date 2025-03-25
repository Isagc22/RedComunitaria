package com.example.proyectotalentotech.controller;

import com.example.proyectotalentotech.model.Usuario;
import com.example.proyectotalentotech.services.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<Usuario> crear(@RequestBody Usuario usuario) {
        return ResponseEntity.ok(usuarioService.guardar(usuario));
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodos() {
        List<Usuario> lista = usuarioService.listarTodos();
        if (lista.isEmpty()) {
            System.out.println("⚠️ La tabla de usuarios está vacía.");
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerPorId(@PathVariable Integer id) {
        Optional<Usuario> entity = usuarioService.obtenerPorId(id);
        if (entity.isEmpty()) {
            System.out.println("No se encontró el usuario con id " + id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(entity.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizar(@PathVariable Integer id, @RequestBody Usuario usuario) {
        Optional<Usuario> entity = usuarioService.obtenerPorId(id);

        if (entity.isPresent()) {
            Usuario usuarioActual = entity.get();
            usuarioActual.setEmail_user(usuario.getEmail_user());
            usuarioActual.setPassword_user(usuario.getPassword_user());
            usuarioActual.setEstado_user(usuario.getEstado_user());
            return ResponseEntity.ok(usuarioService.guardar(usuarioActual));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (!usuarioService.obtenerPorId(id).isPresent()) {
            System.out.println("No se encontró el usuario con id " + id);
            return ResponseEntity.badRequest().build();
        }
        usuarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}