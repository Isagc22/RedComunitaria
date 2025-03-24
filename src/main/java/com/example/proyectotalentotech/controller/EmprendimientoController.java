package com.example.proyectotalentotech.controller;

import com.example.proyectotalentotech.model.Emprendimiento;
import com.example.proyectotalentotech.services.EmprendimientoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/emprendimientos")
public class EmprendimientoController {

    private final EmprendimientoService emprendimientoService;

    public EmprendimientoController(EmprendimientoService emprendimientoService) {
        this.emprendimientoService = emprendimientoService;
    }

    @PostMapping
    public ResponseEntity<Emprendimiento> crearEmprendimiento(@RequestBody Emprendimiento emprendimiento) {
        return ResponseEntity.ok(emprendimientoService.guardar(emprendimiento));
    }

    @GetMapping
    public ResponseEntity<List<Emprendimiento>> listarTodos() {
        return ResponseEntity.ok(emprendimientoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Emprendimiento> obtenerPorId(@PathVariable Integer id) {
        Optional<Emprendimiento> emprendimiento = emprendimientoService.obtenerPorId(id);
        return emprendimiento.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        emprendimientoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
