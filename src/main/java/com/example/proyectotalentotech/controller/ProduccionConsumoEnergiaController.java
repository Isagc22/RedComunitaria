package com.example.proyectotalentotech.controller;

import com.example.proyectotalentotech.model.ProduccionConsumoEnergia;
import com.example.proyectotalentotech.services.ProduccionConsumoEnergiaServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/produccionConsumoEnergia")
public class ProduccionConsumoEnergiaController {

    private final ProduccionConsumoEnergiaServices produccionConsumoEnergiaServices;

    public ProduccionConsumoEnergiaController(ProduccionConsumoEnergiaServices produccionConsumoEnergiaServices) {
        this.produccionConsumoEnergiaServices = produccionConsumoEnergiaServices;
    }

    @PostMapping
    public ResponseEntity<ProduccionConsumoEnergia> crear(@RequestBody ProduccionConsumoEnergia entity) {
        return ResponseEntity.ok(produccionConsumoEnergiaServices.guardar(entity));
    }

    @GetMapping
    public ResponseEntity<List<ProduccionConsumoEnergia>> listarTodos() {
        return ResponseEntity.ok(produccionConsumoEnergiaServices.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProduccionConsumoEnergia> obtenerPorId(@PathVariable Integer id) {
        Optional<ProduccionConsumoEnergia> entity = produccionConsumoEnergiaServices.obtenerPorId(id);
        return entity.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        produccionConsumoEnergiaServices.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}