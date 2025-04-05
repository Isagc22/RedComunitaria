package com.example.proyectotalentotech.controller;

import com.example.proyectotalentotech.model.ComentariosYCalificaciones;
import com.example.proyectotalentotech.services.ComentariosYCalificacionesService;
import com.example.proyectotalentotech.services.EmprendimientoService;
import com.example.proyectotalentotech.services.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para la gestión de comentarios y calificaciones.
 * <p>
 * Esta clase proporciona endpoints para crear, leer, actualizar y eliminar
 * comentarios y calificaciones realizados por los usuarios sobre los emprendimientos.
 * Permite a los usuarios expresar sus opiniones y valorar la calidad de los
 * productos o servicios ofrecidos.
 * </p>
 * 
 * @author Equipo RedComunitaria
 * @version 1.0
 * @since 2023-03-30
 */
@RestController
@RequestMapping("/comentariosYCalificaciones")
public class ComentariosYCalificacionesController {

    private final ComentariosYCalificacionesService comentariosYCalificacionesService;
    private final UsuarioService usuarioService;
    private final EmprendimientoService emprendimientoService;

    /**
     * Constructor para la inyección de dependencias.
     * 
     * @param comentariosYCalificacionesService Servicio para la gestión de comentarios y calificaciones
     * @param usuarioService Servicio para la gestión de usuarios
     * @param emprendimientoService Servicio para la gestión de emprendimientos
     */
    public ComentariosYCalificacionesController(ComentariosYCalificacionesService comentariosYCalificacionesService,
                                                UsuarioService usuarioService,
                                                EmprendimientoService emprendimientoService) {
        this.comentariosYCalificacionesService = comentariosYCalificacionesService;
        this.usuarioService = usuarioService;
        this.emprendimientoService = emprendimientoService;
    }

    /**
     * Crea un nuevo comentario y calificación en el sistema.
     * <p>
     * Verifica que tanto el usuario como el emprendimiento asociados existan
     * antes de crear el comentario y calificación.
     * </p>
     * 
     * @param comentariosycalificaciones El objeto ComentariosYCalificaciones a crear
     * @return ResponseEntity con el comentario y calificación creados o un error si las validaciones fallan
     */
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

    /**
     * Obtiene la lista de todos los comentarios y calificaciones registrados en el sistema.
     * 
     * @return ResponseEntity con la lista de comentarios y calificaciones o un error si la lista está vacía
     */
    @GetMapping
    public ResponseEntity<List<ComentariosYCalificaciones>> listarTodos() {
        List<ComentariosYCalificaciones> lista = comentariosYCalificacionesService.listarTodos();

        if (lista.isEmpty()) {
            System.out.println("⚠️ La tabla de comentarios y calificaciones está vacía.");
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(lista);
    }

    /**
     * Busca un comentario y calificación por su identificador único.
     * 
     * @param id El identificador del comentario y calificación a buscar
     * @return ResponseEntity con el comentario y calificación si se encuentra, o badRequest si no existe
     */
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

    /**
     * Actualiza un comentario y calificación existente.
     * <p>
     * Permite actualizar el comentario, las fechas, la calificación, 
     * y las referencias al emprendimiento y usuario.
     * </p>
     * 
     * @param id El identificador del comentario y calificación a actualizar
     * @param comentariosycalificaciones El objeto ComentariosYCalificaciones con los nuevos datos
     * @return ResponseEntity con el comentario y calificación actualizado, o notFound si no existe
     */
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
            System.out.println("No se encontró el comentario y calificación con id " + id);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Elimina un comentario y calificación por su identificador único.
     * 
     * @param id El identificador del comentario y calificación a eliminar
     * @return ResponseEntity sin contenido (204) si la eliminación fue exitosa, o badRequest si no existe
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {

        if (!comentariosYCalificacionesService.obtenerPorId(id).isPresent()) {
            System.out.println("No se encontró el comentario y calificación con id " + id);
            return ResponseEntity.badRequest().build();
        }

        comentariosYCalificacionesService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}