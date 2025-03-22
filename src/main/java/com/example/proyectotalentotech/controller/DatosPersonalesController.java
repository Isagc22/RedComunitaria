package com.example.proyectotalentotech.controller;

import com.example.proyectotalentotech.model.DatosPersonales;
import com.example.proyectotalentotech.services.DatosPersonalesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/datosPersonales")
public class DatosPersonalesController {

    private final DatosPersonalesService datosPersonalesService;

    public DatosPersonalesController(DatosPersonalesService datosPersonalesService) {
        this.datosPersonalesService = datosPersonalesService;
    }

    @PostMapping
    public ResponseEntity<DatosPersonales> crear(@RequestBody DatosPersonales datosPersonales) {
        return ResponseEntity.ok(datosPersonalesService.guardar(datosPersonales));
    }

    @GetMapping
    public ResponseEntity<List<DatosPersonales>> listarTodos() {
        return ResponseEntity.ok(datosPersonalesService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosPersonales> obtenerPorId(@PathVariable Integer id) {
        Optional<DatosPersonales> datosPersonales = datosPersonalesService.obtenerPorId(id);
        return datosPersonales.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        datosPersonalesService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
