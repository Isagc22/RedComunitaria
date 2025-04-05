package com.example.proyectotalentotech.controller;


import com.example.proyectotalentotech.model.Roles;
import com.example.proyectotalentotech.services.RolesService;
import com.example.proyectotalentotech.services.TipoDocumentoService;
import com.example.proyectotalentotech.services.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para la gestión de roles de usuario.
 * <p>
 * Esta clase proporciona endpoints para realizar operaciones CRUD 
 * (Crear, Leer, Actualizar, Eliminar) sobre los roles que pueden
 * ser asignados a los usuarios en el sistema, estableciendo sus
 * permisos y capacidades.
 * </p>
 * 
 * @author Equipo RedComunitaria
 * @version 1.0
 * @since 2023-03-30
 */
@RestController
@RequestMapping("/roles")
public class RolesController {

    private final RolesService rolesService;
    private final UsuarioService usuarioService;
    private final TipoDocumentoService tipoDocumentoService;

    /**
     * Constructor para la inyección de dependencias.
     * 
     * @param rolesService Servicio para la gestión de roles
     * @param usuarioService Servicio para la gestión de usuarios
     * @param tipoDocumentoService Servicio para la gestión de tipos de documento
     */
    public RolesController(RolesService rolesService, UsuarioService usuarioService, TipoDocumentoService tipoDocumentoService) {
        this.rolesService = rolesService;
        this.usuarioService = usuarioService;
        this.tipoDocumentoService = tipoDocumentoService;
    }

    /**
     * Crea un nuevo rol en el sistema.
     * <p>
     * Verifica que tanto el usuario como el tipo de documento asociados existan
     * antes de crear el rol.
     * </p>
     * 
     * @param rol El objeto Roles a crear
     * @return ResponseEntity con el rol creado o un error si las validaciones fallan
     */
    @PostMapping
    public ResponseEntity<Roles> crear(@RequestBody Roles rol) {
        if (!usuarioService.obtenerPorId(rol.getIdusuarios()).isPresent()) {
            System.out.println("Usuario con id " + rol.getIdusuarios() + " no existe");
            return ResponseEntity.badRequest().body(null);
        }

        if (!tipoDocumentoService.obtenerPorId(rol.getIdtipousuario()).isPresent()) {
            System.out.println("Tipo de documento con id " + rol.getIdtipousuario() + " no existe");
            return ResponseEntity.badRequest().body(null);
        }

        return ResponseEntity.ok(rolesService.guardar(rol));
    }

    /**
     * Obtiene la lista de todos los roles registrados en el sistema.
     * 
     * @return ResponseEntity con la lista de roles o un error si la lista está vacía
     */
    @GetMapping
    public ResponseEntity<List<Roles>> listarTodos() {
        List<Roles> lista = rolesService.listarTodos();
        if (lista.isEmpty()) {
            System.out.println("⚠️ La tabla de roles está vacía.");
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(lista);
    }

    /**
     * Busca un rol por su identificador único.
     * 
     * @param id El identificador del rol a buscar
     * @return ResponseEntity con el rol si se encuentra, o notFound si no existe
     */
    @GetMapping("/{id}")
    public ResponseEntity<Roles> obtenerPorId(@PathVariable Integer id) {
        Optional<Roles> entity = rolesService.obtenerPorId(id);
        if (entity.isEmpty()) {
            System.out.println("No se encontró el rol con id " + id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(entity.get());
    }

    /**
     * Actualiza un rol existente.
     * <p>
     * Permite actualizar las fechas de creación y modificación, así como
     * el usuario y tipo de usuario asociados al rol.
     * </p>
     * 
     * @param id El identificador del rol a actualizar
     * @param rol El objeto Roles con los nuevos datos
     * @return ResponseEntity con el rol actualizado, o notFound si no existe
     */
    @PutMapping("/{id}")
    public ResponseEntity<Roles> actualizar(@PathVariable Integer id, @RequestBody Roles rol) {
        Optional<Roles> entity = rolesService.editarPorId(id);

        if (entity.isPresent()) {
            Roles rolActual = entity.get();
            rolActual.setCreado(rol.getCreado());
            rolActual.setModificado(rol.getModificado());
            rolActual.setIdusuarios(rol.getIdusuarios());
            rolActual.setIdtipousuario(rol.getIdtipousuario());
            return ResponseEntity.ok(rolesService.guardar(rolActual));
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Elimina un rol por su identificador único.
     * 
     * @param id El identificador del rol a eliminar
     * @return ResponseEntity sin contenido (204) si la eliminación fue exitosa, o badRequest si no existe
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (!rolesService.obtenerPorId(id).isPresent()) {
            System.out.println("No se encontró el rol con id " + id);
            return ResponseEntity.badRequest().build();
        }
        rolesService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}