package com.example.proyectotalentotech.controller;

import com.example.proyectotalentotech.model.HistorialEmprendimiento;
import com.example.proyectotalentotech.model.ProduccionConsumoEnergia;
import com.example.proyectotalentotech.services.EmprendimientoService;
import com.example.proyectotalentotech.services.ProduccionConsumoEnergiaServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/produccionConsumoEnergia")
public class ProduccionConsumoEnergiaController {

    private final ProduccionConsumoEnergiaServices produccionConsumoEnergiaServices;
    private final EmprendimientoService emprendimientoService;

    public ProduccionConsumoEnergiaController(ProduccionConsumoEnergiaServices produccionConsumoEnergiaServices, EmprendimientoService emprendimientoService) {
        this.emprendimientoService = emprendimientoService;
        this.produccionConsumoEnergiaServices = produccionConsumoEnergiaServices;
    }

    @PostMapping
    public ResponseEntity<ProduccionConsumoEnergia> crear(@RequestBody ProduccionConsumoEnergia produccionConsumoEnergia) {
        if (!emprendimientoService.obtenerPorId(produccionConsumoEnergia.getIdemprendimiento()).isPresent()){
            System.out.println("Emprendimiento con id: " + produccionConsumoEnergia.getIdemprendimiento()+ " no existe");
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(produccionConsumoEnergiaServices.guardar(produccionConsumoEnergia));
    }

    @GetMapping
    public ResponseEntity<List<ProduccionConsumoEnergia>> listarTodos() {
        List<ProduccionConsumoEnergia> lista = produccionConsumoEnergiaServices.listarTodos();
        if (lista.isEmpty()) {
            System.out.println("⚠️ La tabla de producción y consumo de energía está vacía.");
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProduccionConsumoEnergia> obtenerPorId(@PathVariable Integer id) {
        if(produccionConsumoEnergiaServices.obtenerPorId(id).isEmpty()){
            System.out.println("No se encontró el historial de emprendimiento con id " + id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(produccionConsumoEnergiaServices.obtenerPorId(id).get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProduccionConsumoEnergia> actualizar(@PathVariable Integer id, @RequestBody ProduccionConsumoEnergia produccionConsumoEnergia) {
        Optional<ProduccionConsumoEnergia> entity = produccionConsumoEnergiaServices.editarPorId(id);

        if (entity.isPresent()) {
            ProduccionConsumoEnergia produccionConsumoEnergiaActual = entity.get();
            produccionConsumoEnergiaActual.setProduccion_energia(produccionConsumoEnergia.getProduccion_energia());
            produccionConsumoEnergiaActual.setConsumo_energia(produccionConsumoEnergia.getConsumo_energia());
            produccionConsumoEnergiaActual.setFecha_registro(produccionConsumoEnergia.getFecha_registro());
            produccionConsumoEnergiaActual.setIdemprendimiento(produccionConsumoEnergia.getIdemprendimiento());
            return ResponseEntity.ok(produccionConsumoEnergiaServices.guardar(produccionConsumoEnergiaActual));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (!produccionConsumoEnergiaServices.obtenerPorId(id).isPresent()) {
            System.out.println("No se encontró la producción y consumo de energía con id " + id);
            return ResponseEntity.badRequest().build();
        }
        produccionConsumoEnergiaServices.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}