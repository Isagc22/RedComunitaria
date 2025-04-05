package com.example.proyectotalentotech.controller;

import com.example.proyectotalentotech.model.TipoDocumento;
import com.example.proyectotalentotech.services.TipoDocumentoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para la gestión de tipos de documento.
 * <p>
 * Esta clase proporciona endpoints para realizar operaciones CRUD 
 * (Crear, Leer, Actualizar, Eliminar) sobre los tipos de documento
 * que pueden ser utilizados en el sistema.
 * </p>
 * 
 * @author Equipo RedComunitaria
 * @version 1.0
 * @since 2023-03-30
 */
@RestController
@RequestMapping("/tipodocumento")
public class TipoDocumentoController {

    private final TipoDocumentoService tipoDocumentoService;

    /**
     * Constructor para la inyección de dependencias.
     * 
     * @param tipoDocumentoService Servicio para la gestión de tipos de documento
     */
    public TipoDocumentoController(TipoDocumentoService tipoDocumentoService) {
        this.tipoDocumentoService = tipoDocumentoService;
    }

    /**
     * Crea un nuevo tipo de documento en el sistema.
     * 
     * @param tipoDocumento El objeto TipoDocumento a crear
     * @return ResponseEntity con el tipo de documento creado
     */
    @PostMapping
    public ResponseEntity<TipoDocumento> crear(@RequestBody TipoDocumento tipoDocumento) {
        return ResponseEntity.ok(tipoDocumentoService.guardar(tipoDocumento));
    }

    /**
     * Obtiene la lista de todos los tipos de documento registrados en el sistema.
     * 
     * @return ResponseEntity con la lista de tipos de documento o un error si la lista está vacía
     */
    @GetMapping
    public ResponseEntity<List<TipoDocumento>> listarTodos() {
        List<TipoDocumento> lista = tipoDocumentoService.listarTodos();
        if (lista.isEmpty()) {
            System.out.println("⚠️ La tabla de tipo de documento está vacía.");
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(lista);
    }

    /**
     * Busca un tipo de documento por su identificador único.
     * 
     * @param id El identificador del tipo de documento a buscar
     * @return ResponseEntity con el tipo de documento si se encuentra, o notFound si no existe
     */
    @GetMapping("/{id}")
    public ResponseEntity<TipoDocumento> obtenerPorId(@PathVariable Integer id) {
        Optional<TipoDocumento> entity = tipoDocumentoService.obtenerPorId(id);
        if (entity.isEmpty()) {
            System.out.println("No se encontró el tipo de documento con id " + id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(entity.get());
    }

    /**
     * Actualiza un tipo de documento existente.
     * 
     * @param id El identificador del tipo de documento a actualizar
     * @param tipoDocumento El objeto TipoDocumento con los nuevos datos
     * @return ResponseEntity con el tipo de documento actualizado, o notFound si no existe
     */
    @PutMapping("/{id}")
    public ResponseEntity<TipoDocumento> actualizar(@PathVariable Integer id, @RequestBody TipoDocumento tipoDocumento) {
        Optional<TipoDocumento> entity = tipoDocumentoService.editarPorId(id);

        if (entity.isPresent()) {
            TipoDocumento tipoDocumentoActual = entity.get();
            tipoDocumentoActual.setNombre_tipo_documento(tipoDocumento.getNombre_tipo_documento());
            return ResponseEntity.ok(tipoDocumentoService.guardar(tipoDocumentoActual));
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Elimina un tipo de documento por su identificador único.
     * 
     * @param id El identificador del tipo de documento a eliminar
     * @return ResponseEntity sin contenido (204) si la eliminación fue exitosa, o badRequest si no existe
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (!tipoDocumentoService.obtenerPorId(id).isPresent()) {
            System.out.println("No se encontró el tipo de documento con id " + id);
            return ResponseEntity.badRequest().build();
        }
        tipoDocumentoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}