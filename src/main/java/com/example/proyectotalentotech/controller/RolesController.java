package com.example.proyectotalentotech.controller;

import com.example.proyectotalentotech.model.Roles;
import com.example.proyectotalentotech.services.RolesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RolesController {
    private final RolesService rolesService;

    public RolesController(RolesService rolesService) {
        this.rolesService = rolesService;
    }

    @PostMapping
    public ResponseEntity<Roles> crearRol(@RequestBody Roles rol) {
        return ResponseEntity.ok(rolesService.guardar(rol));
    }

    @GetMapping
    public ResponseEntity<List<Roles>> obtenerRoles() {
        return ResponseEntity.ok(rolesService.listarTodos());
    }
}