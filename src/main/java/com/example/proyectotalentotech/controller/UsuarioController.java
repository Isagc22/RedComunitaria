package com.example.proyectotalentotech.controller;

import com.example.proyectotalentotech.model.Usuario;
import com.example.proyectotalentotech.model.DatosPersonales;
import com.example.proyectotalentotech.repository.DatosPersonalesRepository;
import com.example.proyectotalentotech.services.UsuarioService;
import com.example.proyectotalentotech.services.DatosPersonalesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final DatosPersonalesService datosPersonalesService;
    private final DatosPersonalesRepository datosPersonalesRepository;

    public UsuarioController(UsuarioService usuarioService, DatosPersonalesService datosPersonalesService, DatosPersonalesRepository datosPersonalesRepository) {
        this.usuarioService = usuarioService;
        this.datosPersonalesService = datosPersonalesService;
        this.datosPersonalesRepository = datosPersonalesRepository;
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
            usuarioActual.setEmailUser(usuario.getEmailUser());
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

    @PostMapping("/login")
    public ResponseEntity<?> iniciarSesion(@RequestBody Usuario usuario) {
        Optional<Usuario> usuarioExistente = usuarioService.obtenerPorEmail(usuario.getEmailUser());

        if (usuarioExistente.isEmpty() ||
                !usuarioExistente.get().getPassword_user().equals(usuario.getPassword_user())) {
            return ResponseEntity.status(401).body("Credenciales incorrectas");
        }

        Usuario usuarioAutenticado = usuarioExistente.get();

        // Prueba buscar datos personales con otro método
        Optional<DatosPersonales> datosPersonales = datosPersonalesRepository.findByIdusuarios(usuarioAutenticado.getIdusuarios());

        if (datosPersonales.isEmpty()) {
            System.out.println("⚠️ No se encontraron datos personales para el usuario con ID " + usuarioAutenticado.getIdusuarios());
        }

        // Construir respuesta con usuario y datos personales
        Map<String, Object> response = new HashMap<>();
        response.put("usuario", usuarioAutenticado);
        response.put("datosPersonales", datosPersonales.orElse(null));

        return ResponseEntity.ok(response);
    }

}
