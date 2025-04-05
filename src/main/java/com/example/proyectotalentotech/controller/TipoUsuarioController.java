package com.example.proyectotalentotech.controller;

import com.example.proyectotalentotech.model.TipoUsuario;
import com.example.proyectotalentotech.services.TipoUsuarioServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para la gestión de tipos de usuario.
 * <p>
 * Esta clase proporciona endpoints para realizar operaciones CRUD 
 * (Crear, Leer, Actualizar, Eliminar) sobre los tipos de usuario
 * que pueden ser asignados en el sistema.
 * </p>
 * 
 * @author Equipo RedComunitaria
 * @version 1.0
 * @since 2023-03-30
 */
@RestController
@RequestMapping("/tipousuario")
public class TipoUsuarioController {

    private final TipoUsuarioServices tipoUsuarioService;

    /**
     * Constructor para la inyección de dependencias.
     * 
     * @param tipoUsuarioService Servicio para la gestión de tipos de usuario
     */
    public TipoUsuarioController(TipoUsuarioServices tipoUsuarioService) {
        this.tipoUsuarioService = tipoUsuarioService;
    }

    /**
     * Crea un nuevo tipo de usuario en el sistema.
     * 
     * @param tipoUsuario El objeto TipoUsuario a crear
     * @return ResponseEntity con el tipo de usuario creado
     */
    @PostMapping
    public ResponseEntity<TipoUsuario> crear(@RequestBody TipoUsuario tipoUsuario) {
        return ResponseEntity.ok(tipoUsuarioService.guardar(tipoUsuario));
    }

    /**
     * Obtiene la lista de todos los tipos de usuario registrados en el sistema.
     * 
     * @return ResponseEntity con la lista de tipos de usuario o un error si la lista está vacía
     */
    @GetMapping
    public ResponseEntity<List<TipoUsuario>> listarTodos() {
        List<TipoUsuario> lista = tipoUsuarioService.listarTodos();
        if (lista.isEmpty()) {
            System.out.println("⚠️ La tabla de tipo de usuario está vacía.");
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(lista);
    }

    /**
     * Busca un tipo de usuario por su identificador único.
     * 
     * @param id El identificador del tipo de usuario a buscar
     * @return ResponseEntity con el tipo de usuario si se encuentra, o notFound si no existe
     */
    @GetMapping("/{id}")
    public ResponseEntity<TipoUsuario> obtenerPorId(@PathVariable Integer id) {
        Optional<TipoUsuario> entity = tipoUsuarioService.obtenerPorId(id);
        if (entity.isEmpty()) {
            System.out.println("No se encontró el tipo de usuario con id " + id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(entity.get());
    }

    /**
     * Actualiza un tipo de usuario existente.
     * <p>
     * Este método actualiza tanto el nombre como el estado del tipo de usuario.
     * </p>
     * 
     * @param id El identificador del tipo de usuario a actualizar
     * @param tipoUsuario El objeto TipoUsuario con los nuevos datos
     * @return ResponseEntity con el tipo de usuario actualizado, o notFound si no existe
     */
    @PutMapping("/{id}")
    public ResponseEntity<TipoUsuario> actualizar(@PathVariable Integer id, @RequestBody TipoUsuario tipoUsuario) {
        Optional<TipoUsuario> entity = tipoUsuarioService.editarPorId(id);

        if (entity.isPresent()) {
            TipoUsuario tipoUsuarioActual = entity.get();
            tipoUsuarioActual.setNombre_tipo_usuario(tipoUsuario.getNombre_tipo_usuario());
            tipoUsuarioActual.setEstado_tipo_usuario(tipoUsuario.isEstado_tipo_usuario());
            return ResponseEntity.ok(tipoUsuarioService.guardar(tipoUsuarioActual));
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Elimina un tipo de usuario por su identificador único.
     * 
     * @param id El identificador del tipo de usuario a eliminar
     * @return ResponseEntity sin contenido (204) si la eliminación fue exitosa, o badRequest si no existe
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (!tipoUsuarioService.obtenerPorId(id).isPresent()) {
            System.out.println("No se encontró el tipo de usuario con id " + id);
            return ResponseEntity.badRequest().build();
        }
        tipoUsuarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}