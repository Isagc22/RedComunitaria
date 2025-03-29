package com.example.proyectotalentotech.controller;


import com.example.proyectotalentotech.model.Roles;
import com.example.proyectotalentotech.services.RolesService;
import com.example.proyectotalentotech.services.TipoDocumentoService;
import com.example.proyectotalentotech.services.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/roles")
public class RolesController {

    private final RolesService rolesService;
    private final UsuarioService usuarioService;
    private final TipoDocumentoService tipoDocumentoService;

    public RolesController(RolesService rolesService, UsuarioService usuarioService, TipoDocumentoService tipoDocumentoService) {
        this.rolesService = rolesService;
        this.usuarioService = usuarioService;
        this.tipoDocumentoService = tipoDocumentoService;
    }

    @PostMapping
    public ResponseEntity<Roles> crear(@RequestBody Roles rol) {
        if (!usuarioService.obtenerPorId(rol.getIdusuarios()).isPresent()) {
            System.out.println("Usuario con id " + rol.getIdusuarios() + " no existe");
            return ResponseEntity.badRequest().body(null);
        }

        if (!tipoDocumentoService.obtenerPorId(rol.getIdtipousuario()).isPresent()) {
            System.out.println("Tipo de documento con id " + rol.getIdtipousuario() + " no existe");
            return ResponseEntity.badRequest().body(null);
        }

        return ResponseEntity.ok(rolesService.guardar(rol));
    }

    @GetMapping
    public ResponseEntity<List<Roles>> listarTodos() {
        List<Roles> lista = rolesService.listarTodos();
        if (lista.isEmpty()) {
            System.out.println("⚠️ La tabla de roles está vacía.");
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Roles> obtenerPorId(@PathVariable Integer id) {
        Optional<Roles> entity = rolesService.obtenerPorId(id);
        if (entity.isEmpty()) {
            System.out.println("No se encontró el rol con id " + id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(entity.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Roles> actualizar(@PathVariable Integer id, @RequestBody Roles rol) {
        Optional<Roles> entity = rolesService.editarPorId(id);

        if (entity.isPresent()) {
            Roles rolActual = entity.get();
            rolActual.setCreado(rol.getCreado());
            rolActual.setModificado(rol.getModificado());
            rolActual.setIdusuarios(rol.getIdusuarios());
            rolActual.setIdtipousuario(rol.getIdtipousuario());
            return ResponseEntity.ok(rolesService.guardar(rolActual));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (!rolesService.obtenerPorId(id).isPresent()) {
            System.out.println("No se encontró el rol con id " + id);
            return ResponseEntity.badRequest().build();
        }
        rolesService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}