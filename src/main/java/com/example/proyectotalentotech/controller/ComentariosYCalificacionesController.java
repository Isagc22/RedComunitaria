package com.example.proyectotalentotech.controller;

import com.example.proyectotalentotech.model.ComentariosYCalificaciones;
import com.example.proyectotalentotech.services.ComentariosYCalificacionesService;
import com.example.proyectotalentotech.services.EmprendimientoService;
import com.example.proyectotalentotech.services.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/comentariosYCalificaciones")
public class ComentariosYCalificacionesController {

    private final ComentariosYCalificacionesService comentariosYCalificacionesService;
    private final UsuarioService usuarioService;
    private final EmprendimientoService emprendimientoService;

    public ComentariosYCalificacionesController(ComentariosYCalificacionesService comentariosYCalificacionesService,
                                                UsuarioService usuarioService,
                                                EmprendimientoService emprendimientoService) {
        this.comentariosYCalificacionesService = comentariosYCalificacionesService;
        this.usuarioService = usuarioService;
        this.emprendimientoService = emprendimientoService;
    }

    @PostMapping
    public ResponseEntity<ComentariosYCalificaciones> crear(@RequestBody ComentariosYCalificaciones comentariosycalificaciones) {

        if (!usuarioService.obtenerPorId(comentariosycalificaciones.getIdusuarios()).isPresent()) {
            System.out.println("Usuario con id " + comentariosycalificaciones.getIdusuarios() + " no existe");
            return ResponseEntity.badRequest().body(null);
        }

        if (!emprendimientoService.obtenerPorId(comentariosycalificaciones.getIdemprendimiento()).isPresent()) {
            System.out.println("Emprendimiento con id " + comentariosycalificaciones.getIdemprendimiento() + " no existe");
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(comentariosYCalificacionesService.guardar(comentariosycalificaciones));
    }

    @GetMapping
    public ResponseEntity<List<ComentariosYCalificaciones>> listarTodos() {
        List<ComentariosYCalificaciones> lista = comentariosYCalificacionesService.listarTodos();

        if (lista.isEmpty()) {
            System.out.println("⚠️ La tabla de comentarios y calificaciones está vacía.");
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComentariosYCalificaciones> obtenerPorId(@PathVariable Integer id) {
        Optional<ComentariosYCalificaciones> entity = comentariosYCalificacionesService.obtenerPorId(id);

        if (entity.isEmpty()) {
            System.out.println("⚠️ No se encontró el comentario y calificación con id " + id);
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(entity.get());

        //return entity.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ComentariosYCalificaciones> editarPorId(@PathVariable Integer id, @RequestBody ComentariosYCalificaciones comentariosycalificaciones) {
        Optional<ComentariosYCalificaciones> entity = comentariosYCalificacionesService.editarPorId(id);
        if (entity.isPresent()) {
            ComentariosYCalificaciones comentariosycalificacionesActual = entity.get();
            comentariosycalificacionesActual.setComentario(comentariosycalificaciones.getComentario());
            comentariosycalificacionesActual.setFecha_registro(comentariosycalificaciones.getFecha_registro());
            comentariosycalificacionesActual.setFecha_comentario(comentariosycalificaciones.getFecha_comentario());
            comentariosycalificacionesActual.setCalificacion(comentariosycalificaciones.getCalificacion());
            comentariosycalificacionesActual.setIdemprendimiento(comentariosycalificaciones.getIdemprendimiento());
            comentariosycalificacionesActual.setIdusuarios(comentariosycalificaciones.getIdusuarios());
            return ResponseEntity.ok(comentariosYCalificacionesService.guardar(comentariosycalificacionesActual));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        comentariosYCalificacionesService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}