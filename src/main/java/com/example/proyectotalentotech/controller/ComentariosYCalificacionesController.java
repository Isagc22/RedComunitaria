package com.example.proyectotalentotech.controller;

import com.example.proyectotalentotech.model.ComentariosYCalificaciones;
import com.example.proyectotalentotech.services.ComentariosYCalificacionesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/comentariosYCalificaciones")
public class ComentariosYCalificacionesController {

    private final ComentariosYCalificacionesService comentariosYCalificacionesService;

    public ComentariosYCalificacionesController(ComentariosYCalificacionesService comentariosYCalificacionesService) {
        this.comentariosYCalificacionesService = comentariosYCalificacionesService;
    }

    @PostMapping
    public ResponseEntity<ComentariosYCalificaciones> crear(@RequestBody ComentariosYCalificaciones entity) {
        return ResponseEntity.ok(comentariosYCalificacionesService.guardar(entity));
    }

    @GetMapping
    public ResponseEntity<List<ComentariosYCalificaciones>> listarTodos() {
        return ResponseEntity.ok(comentariosYCalificacionesService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComentariosYCalificaciones> obtenerPorId(@PathVariable Integer id) {
        Optional<ComentariosYCalificaciones> entity = comentariosYCalificacionesService.obtenerPorId(id);
        return entity.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        comentariosYCalificacionesService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
