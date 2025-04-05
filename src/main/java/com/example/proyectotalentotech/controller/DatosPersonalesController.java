package com.example.proyectotalentotech.controller;

import com.example.proyectotalentotech.model.DatosPersonales;
import com.example.proyectotalentotech.services.DatosPersonalesService;
import com.example.proyectotalentotech.services.TipoDocumentoService;
import com.example.proyectotalentotech.services.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para la gestión de datos personales de los usuarios.
 * <p>
 * Esta clase proporciona endpoints para crear, leer, actualizar y eliminar
 * datos personales, así como funcionalidades específicas como la carga de imágenes
 * de perfil y la búsqueda de datos por usuario.
 * </p>
 * 
 * @author Equipo RedComunitaria
 * @version 1.0
 * @since 2023-03-30
 */
@RestController
@RequestMapping("/datosPersonales")
public class DatosPersonalesController {

    private final DatosPersonalesService datosPersonalesService;
    private final UsuarioService usuarioService;
    private final TipoDocumentoService tipoDocumentoService;

    /**
     * Constructor del controlador que recibe los servicios necesarios mediante inyección de dependencias.
     * 
     * @param datosPersonalesService Servicio para la gestión de datos personales
     * @param usuarioService Servicio para la gestión de usuarios
     * @param tipoDocumentoService Servicio para la gestión de tipos de documento
     */
    public DatosPersonalesController(DatosPersonalesService datosPersonalesService,
                                     UsuarioService usuarioService,
                                     TipoDocumentoService tipoDocumentoService) {
        this.datosPersonalesService = datosPersonalesService;
        this.usuarioService = usuarioService;
        this.tipoDocumentoService = tipoDocumentoService;
    }

    /**
     * Crea un nuevo registro de datos personales con soporte para carga de imágenes.
     * <p>
     * Este endpoint acepta datos de formulario multipart, permitiendo la carga de una
     * imagen de perfil opcional junto con los datos personales del usuario.
     * </p>
     * 
     * @param nombreCompleto Nombre completo del usuario
     * @param cedula Número de cédula o documento de identidad
     * @param direccion Dirección física del usuario
     * @param telefono Número telefónico de contacto
     * @param idusuarios ID del usuario asociado a estos datos personales
     * @param idtipodocumento ID del tipo de documento
     * @param imagen Archivo de imagen de perfil (opcional)
     * @return ResponseEntity con los datos personales creados o un mensaje de error
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DatosPersonales> crearDatosPersonales(
            @RequestParam("nombre_completo") String nombreCompleto,
            @RequestParam("cedula") String cedula,
            @RequestParam("direccion") String direccion,
            @RequestParam("telefono") String telefono,
            @RequestParam("idusuarios") Integer idusuarios,
            @RequestParam("idtipodocumento") Integer idtipodocumento,
            @RequestParam(value = "imagen", required = false) MultipartFile imagen) {

        // Verifica que el usuario exista
        if (!usuarioService.obtenerPorId(idusuarios).isPresent()) {
            System.out.println("Usuario con id " + idusuarios + " no existe");
            return ResponseEntity.badRequest().body(null);
        }

        // Verifica que el tipo de documento exista
        if (!tipoDocumentoService.obtenerPorId(idtipodocumento).isPresent()) {
            System.out.println("Tipo de documento con id " + idtipodocumento + " no existe");
            return ResponseEntity.badRequest().body(null);
        }

        DatosPersonales datos = new DatosPersonales();
        datos.setNombre_completo(nombreCompleto);
        datos.setCedula(cedula);
        datos.setDireccion(direccion);
        datos.setTelefono(telefono);
        datos.setIdusuarios(idusuarios);
        datos.setIdtipodocumento(idtipodocumento);

        // Si se envía imagen, la convierte a byte[]
        if (imagen != null && !imagen.isEmpty()) {
            try {
                datos.setImagen(imagen.getBytes());
            } catch (IOException e) {
                System.out.println("Error al procesar la imagen: " + e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
        }

        DatosPersonales datosGuardados = datosPersonalesService.guardar(datos);
        return ResponseEntity.ok(datosGuardados);
    }

    /**
     * Obtiene la lista de todos los datos personales registrados en el sistema.
     * 
     * @return ResponseEntity con la lista de datos personales o un mensaje de error si la lista está vacía
     */
    @GetMapping
    public ResponseEntity<List<DatosPersonales>> listarTodos() {
        List<DatosPersonales> lista = datosPersonalesService.listarTodos();

        if (lista.isEmpty()){
            System.out.println("⚠️ La tabla de datos personales está vacía.");
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(lista);
    }

    /**
     * Obtiene los datos personales correspondientes al ID proporcionado.
     * 
     * @param id Identificador único de los datos personales
     * @return ResponseEntity con los datos personales encontrados o un mensaje de error
     */
    @GetMapping("/{id}")
    public ResponseEntity<DatosPersonales> obtenerPorId(@PathVariable Integer id) {
        Optional<DatosPersonales> entity = datosPersonalesService.obtenerPorId(id);
        if (entity.isEmpty()) {
            System.out.println("No se encontró el dato personal con id " + id);
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(entity.get());
    }

    /**
     * Actualiza los datos personales existentes con soporte para actualización de imagen.
     * <p>
     * Este endpoint acepta datos de formulario multipart, permitiendo la actualización de
     * la imagen de perfil junto con los demás datos personales.
     * </p>
     * 
     * @param id Identificador único de los datos personales a actualizar
     * @param nombreCompleto Nuevo nombre completo del usuario
     * @param cedula Nuevo número de cédula o documento de identidad
     * @param direccion Nueva dirección física del usuario
     * @param telefono Nuevo número telefónico de contacto
     * @param idusuarios ID del usuario asociado a estos datos personales
     * @param idtipodocumento ID del tipo de documento
     * @param imagen Nuevo archivo de imagen de perfil (opcional)
     * @return ResponseEntity con los datos personales actualizados o un mensaje de error
     */
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DatosPersonales> editarPorIdMultipart(
            @PathVariable Integer id,
            @RequestParam("nombre_completo") String nombreCompleto,
            @RequestParam("cedula") String cedula,
            @RequestParam("direccion") String direccion,
            @RequestParam("telefono") String telefono,
            @RequestParam("idusuarios") Integer idusuarios,
            @RequestParam("idtipodocumento") Integer idtipodocumento,
            @RequestParam(value = "imagen", required = false) MultipartFile imagen) {

        Optional<DatosPersonales> entity = datosPersonalesService.editarPorId(id);
        if (entity.isPresent()) {
            DatosPersonales datos = entity.get();
            datos.setNombre_completo(nombreCompleto);
            datos.setCedula(cedula);
            datos.setDireccion(direccion);
            datos.setTelefono(telefono);
            datos.setIdusuarios(idusuarios);
            datos.setIdtipodocumento(idtipodocumento);

            if (imagen != null && !imagen.isEmpty()) {
                try {
                    datos.setImagen(imagen.getBytes());
                } catch (IOException e) {
                    System.out.println("Error al procesar la imagen: " + e.getMessage());
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
                }
            }

            if (!usuarioService.obtenerPorId(datos.getIdusuarios()).isPresent()) {
                System.out.println("Usuario con id " + datos.getIdusuarios() + " no existe");
                return ResponseEntity.badRequest().body(null);
            }
            if (!tipoDocumentoService.obtenerPorId(datos.getIdtipodocumento()).isPresent()) {
                System.out.println("Tipo de documento con id " + datos.getIdtipodocumento() + " no existe");
                return ResponseEntity.badRequest().body(null);
            }
            return ResponseEntity.ok(datosPersonalesService.guardar(datos));
        } else {
            System.out.println("No se encontró el dato personal con id " + id);
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Elimina los datos personales correspondientes al ID proporcionado.
     * 
     * @param id Identificador único de los datos personales a eliminar
     * @return ResponseEntity sin contenido (204) si la eliminación fue exitosa o un mensaje de error
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (!datosPersonalesService.obtenerPorId(id).isPresent()) {
            System.out.println("Usuario con id " + id + " no existe");
            return ResponseEntity.badRequest().build();
        }
        datosPersonalesService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Obtiene los datos personales correspondientes al usuario proporcionado.
     * <p>
     * Este endpoint permite buscar datos personales por el ID del usuario asociado,
     * facilitando la recuperación de información personal desde el perfil de usuario.
     * </p>
     * 
     * @param idusuarios Identificador del usuario cuyos datos personales se desean obtener
     * @return ResponseEntity con los datos personales encontrados o un mensaje de error
     */
    @GetMapping("/usuario/{idusuarios}")
    public ResponseEntity<DatosPersonales> obtenerPorUsuario(@PathVariable Integer idusuarios) {
        try {
            System.out.println("Buscando datos personales para el usuario con ID: " + idusuarios);
            Optional<DatosPersonales> datos = datosPersonalesService.obtenerPorIdUsuario(idusuarios);
            if (datos.isPresent()) {
                System.out.println("Datos personales encontrados para el usuario con ID: " + idusuarios);
                return ResponseEntity.ok(datos.get());
            } else {
                System.out.println("No se encontraron datos personales para el usuario con ID: " + idusuarios);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            System.err.println("Error al obtener datos personales para el usuario con ID " + idusuarios + ": " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
