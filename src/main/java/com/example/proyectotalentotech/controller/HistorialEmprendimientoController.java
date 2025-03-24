package com.example.proyectotalentotech.controller;

import com.example.proyectotalentotech.model.HistorialEmprendimiento;
import com.example.proyectotalentotech.services.HistorialEmprendimientoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/historialEmprendimiento")
public class HistorialEmprendimientoController {

    private final HistorialEmprendimientoService historialEmprendimientoService;

    public HistorialEmprendimientoController(HistorialEmprendimientoService historialEmprendimientoService) {
        this.historialEmprendimientoService = historialEmprendimientoService;
    }

    @PostMapping
    public ResponseEntity<HistorialEmprendimiento> crear(@RequestBody HistorialEmprendimiento historialEmprendimiento) {
        return ResponseEntity.ok(historialEmprendimientoService.guardar(historialEmprendimiento));
    }

    @GetMapping
    public ResponseEntity<List<HistorialEmprendimiento>> listarTodos() {
        return ResponseEntity.ok(historialEmprendimientoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistorialEmprendimiento> obtenerPorId(@PathVariable Integer id) {
        Optional<HistorialEmprendimiento> historial = historialEmprendimientoService.obtenerPorId(id);
        return historial.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        historialEmprendimientoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}