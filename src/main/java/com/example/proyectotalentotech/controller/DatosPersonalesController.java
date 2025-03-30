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

@RestController
@RequestMapping("/datosPersonales")
public class DatosPersonalesController {

    private final DatosPersonalesService datosPersonalesService;
    private final UsuarioService usuarioService;
    private final TipoDocumentoService tipoDocumentoService;

    public DatosPersonalesController(DatosPersonalesService datosPersonalesService,
                                     UsuarioService usuarioService,
                                     TipoDocumentoService tipoDocumentoService) {
        this.datosPersonalesService = datosPersonalesService;
        this.usuarioService = usuarioService;
        this.tipoDocumentoService = tipoDocumentoService;
    }

    // Nuevo método que acepta multipart/form-data
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

    // Métodos existentes sin cambios
    @GetMapping
    public ResponseEntity<List<DatosPersonales>> listarTodos() {
        List<DatosPersonales> lista = datosPersonalesService.listarTodos();

        if (lista.isEmpty()){
            System.out.println("⚠️ La tabla de datos personales está vacía.");
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosPersonales> obtenerPorId(@PathVariable Integer id) {
        Optional<DatosPersonales> entity = datosPersonalesService.obtenerPorId(id);
        if (entity.isEmpty()) {
            System.out.println("No se encontró el dato personal con id " + id);
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(entity.get());
    }


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


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (!datosPersonalesService.obtenerPorId(id).isPresent()) {
            System.out.println("Usuario con id " + id + " no existe");
            return ResponseEntity.badRequest().build();
        }
        datosPersonalesService.eliminar(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/usuario/{idusuarios}")
    public ResponseEntity<DatosPersonales> obtenerPorUsuario(@PathVariable Integer idusuarios) {
        Optional<DatosPersonales> datos = datosPersonalesService.obtenerPorIdUsuario(idusuarios);
        return datos.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
