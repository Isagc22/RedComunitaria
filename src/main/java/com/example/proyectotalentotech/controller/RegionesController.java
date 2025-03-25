package com.example.proyectotalentotech.controller;

import com.example.proyectotalentotech.model.Regiones;
import com.example.proyectotalentotech.services.RegionesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/regiones")
public class RegionesController {
    private final RegionesService regionesService;

    public RegionesController(RegionesService regionesService) {
        this.regionesService = regionesService;
    }

    @PostMapping
    public ResponseEntity<Regiones> crearRegion(@RequestBody Regiones region) {
        return ResponseEntity.ok(regionesService.guardar(region));
    }

    @GetMapping
    public ResponseEntity<List<Regiones>> obtenerRegiones() {
        return ResponseEntity.ok(regionesService.listarTodos());
    }
}