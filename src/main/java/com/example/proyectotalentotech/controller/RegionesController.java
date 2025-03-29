package com.example.proyectotalentotech.controller;

import com.example.proyectotalentotech.model.ProduccionConsumoEnergia;
import com.example.proyectotalentotech.model.Regiones;
import com.example.proyectotalentotech.services.RegionesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<List<Regiones>> listarTodos() {
        List<Regiones> lista = regionesService.listarTodos();
        if (lista.isEmpty()) {
            System.out.println("⚠️ La tabla de producción y consumo de energía está vacía.");
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Regiones> obtenerPorId(@PathVariable Integer id) {
        if(regionesService.obtenerPorId(id).isEmpty()){
            System.out.println("No se encontró la región con id " + id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(regionesService.obtenerPorId(id).get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Regiones> actualizar(@PathVariable Integer id, @RequestBody Regiones region) {
        Optional<Regiones> entity = regionesService.obtenerPorId(id);

        if (entity.isPresent()) {
            Regiones regionActual = entity.get();
            regionActual.setNombre_region(region.getNombre_region());
            return ResponseEntity.ok(regionesService.guardar(regionActual));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Regiones> eliminar(@PathVariable Integer id) {
        Optional<Regiones> entity = regionesService.obtenerPorId(id);
        if (entity.isPresent()) {
            regionesService.eliminar(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}