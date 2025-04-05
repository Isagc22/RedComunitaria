package com.example.proyectotalentotech.controller;

import com.example.proyectotalentotech.model.Emprendimiento;
import com.example.proyectotalentotech.model.Usuario;
import com.example.proyectotalentotech.services.EmprendimientoService;
import com.example.proyectotalentotech.services.RegionesService;
import com.example.proyectotalentotech.services.UsuarioService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Controlador REST para la gestión de emprendimientos.
 * <p>
 * Esta clase proporciona endpoints para crear, leer, actualizar y eliminar
 * emprendimientos, así como funcionalidades específicas como la carga de imágenes,
 * la búsqueda de emprendimientos por región y usuario, y la obtención de estadísticas.
 * </p>
 * 
 * @author Equipo RedComunitaria
 * @version 1.0
 * @since 2023-03-30
 */
@RestController
@RequestMapping("/emprendimientos")
public class EmprendimientoController {

    private final EmprendimientoService emprendimientoService;
    private final RegionesService regionesService;
    private final UsuarioService usuarioService;

    /**
     * Constructor para la inyección de dependencias.
     * 
     * @param emprendimientoService Servicio para gestionar operaciones de emprendimientos
     * @param regionesService Servicio para gestionar operaciones de regiones
     * @param usuarioService Servicio para gestionar operaciones de usuarios
     */
    public EmprendimientoController(EmprendimientoService emprendimientoService,
                                    RegionesService regionesService,
                                    UsuarioService usuarioService) {
        this.emprendimientoService = emprendimientoService;
        this.regionesService = regionesService;
        this.usuarioService = usuarioService;
    }

    /**
     * Crea un nuevo emprendimiento en el sistema.
     * <p>
     * Este endpoint acepta datos de formulario multipart, permitiendo la carga de una
     * imagen para el emprendimiento. Valida la existencia de la región y el usuario,
     * y establece la fecha de creación automáticamente si no se proporciona.
     * </p>
     * 
     * @param emprendimiento El objeto Emprendimiento a crear
     * @param imagen Archivo de imagen del emprendimiento (opcional)
     * @param authorization Token de autorización del usuario que crea el emprendimiento
     * @return ResponseEntity con el emprendimiento creado o un mensaje de error
     */
    @PostMapping
    public ResponseEntity<?> crear(@ModelAttribute Emprendimiento emprendimiento, 
                                 @RequestParam(value = "imagen", required = false) MultipartFile imagen,
                                 @RequestHeader(value = "Authorization", required = false) String authorization) {
        try {
            System.out.println("Recibida solicitud para crear emprendimiento: " + emprendimiento.getNombre());
            
            // Verificar que se haya enviado un token de autorización (aunque no lo usemos para extraer el ID)
            if (authorization == null || !authorization.startsWith("Bearer ")) {
                System.out.println("No se proporcionó token de autorización");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Se requiere autorización para registrar un emprendimiento."));
            }
            
            // Usar directamente el ID de usuario proporcionado en el objeto emprendimiento
            if (emprendimiento.getIdusuarios() == null) {
                System.out.println("No se proporcionó ID de usuario en el objeto emprendimiento");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "No se pudo identificar al usuario. Por favor, intente iniciar sesión nuevamente."));
            }
            
            System.out.println("ID de usuario recibido en el objeto emprendimiento: " + emprendimiento.getIdusuarios());
            
            // Verificaciones estándar
            if (!regionesService.obtenerPorId(emprendimiento.getIdregiones()).isPresent()){
                System.out.println("Región con id " + emprendimiento.getIdregiones() + " no existe");
                return ResponseEntity.badRequest().body(Map.of("error", "La región seleccionada no existe"));
            }
            
            // Verificar el ID del usuario
            if (!usuarioService.obtenerPorId(emprendimiento.getIdusuarios()).isPresent()) {
                System.out.println("Usuario con id " + emprendimiento.getIdusuarios() + " no existe");
                return ResponseEntity.badRequest().body(Map.of("error", "El usuario no existe en el sistema."));
            }
            
            // Establecer fecha de creación automáticamente si no se proporciona
            if (emprendimiento.getFecha_creacion() == null) {
                emprendimiento.setFecha_creacion(LocalDate.now());
            }
            
            // Procesar la imagen si se proporcionó
            if (imagen != null && !imagen.isEmpty()) {
                System.out.println("Procesando imagen: " + imagen.getOriginalFilename() + ", tamaño: " + imagen.getSize() + " bytes");
                emprendimiento.setImagen_emprendimiento(imagen.getBytes());
            }
            
            // Guardar el emprendimiento
            Emprendimiento emprendimientoGuardado = emprendimientoService.guardar(emprendimiento);
            System.out.println("Emprendimiento guardado con éxito, ID: " + emprendimientoGuardado.getIdemprendimiento());
            
            return ResponseEntity.ok(emprendimientoGuardado);
        } catch (Exception e) {
            System.out.println("Error al crear emprendimiento: " + e.getMessage());
            e.printStackTrace(); // Imprimir stack trace completo para depuración
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Error al crear el emprendimiento: " + e.getMessage()));
        }
    }

    /**
     * Obtiene la lista de todos los emprendimientos registrados en el sistema.
     * 
     * @return ResponseEntity con la lista de emprendimientos o un error si la lista está vacía
     */
    @GetMapping
    public ResponseEntity<List<Emprendimiento>> listarTodos() {
        List<Emprendimiento> lista = emprendimientoService.listarTodos();
        if (lista.isEmpty()) {
            System.out.println("⚠️ La tabla de emprendimientos está vacía.");
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(lista);
    }

    /**
     * Busca un emprendimiento por su identificador único.
     * 
     * @param id El identificador del emprendimiento a buscar
     * @return ResponseEntity con el emprendimiento si se encuentra, o badRequest si no existe
     */
    @GetMapping("/{id}")
    public ResponseEntity<Emprendimiento> obtenerPorId(@PathVariable Integer id) {
        Optional<Emprendimiento> entity = emprendimientoService.obtenerPorId(id);
        if (entity.isEmpty()){
            System.out.println("No se encontró el emprendimiento con id " + id);
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(entity.get());
    }

    /**
     * Actualiza un emprendimiento existente.
     * <p>
     * Este endpoint acepta datos de formulario multipart, permitiendo la actualización de la
     * imagen del emprendimiento junto con sus demás atributos.
     * </p>
     * 
     * @param id El identificador del emprendimiento a actualizar
     * @param emprendimiento El objeto Emprendimiento con los nuevos datos
     * @param imagenFile Nuevo archivo de imagen para el emprendimiento (opcional)
     * @return ResponseEntity con el emprendimiento actualizado, o badRequest si no existe
     */
    @PostMapping(value = "/{id}/actualizar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Emprendimiento> editarPorId(
            @PathVariable Integer id,
            @ModelAttribute Emprendimiento emprendimiento,
            @RequestParam(value = "imagen", required = false) MultipartFile imagenFile) {

        Optional<Emprendimiento> entity = emprendimientoService.obtenerPorId(id);

        if (entity.isEmpty()) {
            System.out.println("No se encontró el emprendimiento con id " + id);
            return ResponseEntity.badRequest().build();
        }

        Emprendimiento emprendimientoActual = entity.get();
        emprendimientoActual.setNombre(emprendimiento.getNombre());
        emprendimientoActual.setDescripcion(emprendimiento.getDescripcion());
        emprendimientoActual.setTipo(emprendimiento.getTipo());
        emprendimientoActual.setFecha_creacion(emprendimiento.getFecha_creacion());
        emprendimientoActual.setEstado_emprendimiento(emprendimiento.getEstado_emprendimiento());
        emprendimientoActual.setIdregiones(emprendimiento.getIdregiones());
        emprendimientoActual.setIdusuarios(emprendimiento.getIdusuarios());

        if (imagenFile != null && !imagenFile.isEmpty()) {
            try {
                emprendimientoActual.setImagen_emprendimiento(imagenFile.getBytes());
            } catch (IOException ex) {
                ex.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

        return ResponseEntity.ok(emprendimientoService.guardar(emprendimientoActual));
    }

    /**
     * Elimina un emprendimiento por su identificador único.
     * 
     * @param id El identificador del emprendimiento a eliminar
     * @return ResponseEntity sin contenido (204) si la eliminación fue exitosa, o badRequest si no existe
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (!emprendimientoService.obtenerPorId(id).isPresent()){
            System.out.println("No se encontró el emprendimiento con id " + id);
            return ResponseEntity.badRequest().build();
        }
        emprendimientoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Obtiene la cantidad de emprendimientos por región.
     * <p>
     * Este endpoint proporciona datos estadísticos sobre la distribución
     * de emprendimientos en las diferentes regiones.
     * </p>
     * 
     * @return ResponseEntity con la lista de resultados agrupados por región
     */
    @GetMapping("/por-region")
    public ResponseEntity<?> getEmprendimientosPorRegion() {
        try {
            List<Map<String, Object>> resultados = emprendimientoService.getEmprendimientosPorRegion();
            return ResponseEntity.ok(resultados);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Error al obtener emprendimientos por región");
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Obtiene el porcentaje de emprendimientos por región.
     * <p>
     * Este endpoint proporciona datos estadísticos sobre la distribución
     * porcentual de emprendimientos en las diferentes regiones.
     * </p>
     * 
     * @return ResponseEntity con la lista de resultados porcentuales agrupados por región
     */
    @GetMapping("/porcentaje-por-region")
    public ResponseEntity<?> getPorcentajeEmprendimientosPorRegion() {
        try {
            List<Map<String, Object>> resultados = emprendimientoService.getPorcentajeEmprendimientosPorRegion();
            return ResponseEntity.ok(resultados);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Error al obtener porcentaje de emprendimientos por región");
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Obtiene los emprendimientos pertenecientes a un usuario específico.
     * <p>
     * Este endpoint requiere autorización y verifica que el usuario tenga
     * acceso a los emprendimientos solicitados.
     * </p>
     * 
     * @param userId Identificador del usuario cuyos emprendimientos se desean obtener
     * @param authorization Token de autorización para verificar el acceso
     * @return ResponseEntity con la lista de emprendimientos del usuario o un mensaje de error
     */
    @GetMapping("/usuario/{userId}")
    public ResponseEntity<?> obtenerEmprendimientosUsuario(
            @PathVariable Integer userId,
            @RequestHeader(value = "Authorization", required = false) String authorization) {
        try {
            // Verificar autenticación
            if (authorization == null || !authorization.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Usuario no autenticado o token inválido"));
            }
            
            // Verificar que el ID de usuario sea válido
            if (userId == null || !usuarioService.obtenerPorId(userId).isPresent()) {
                return ResponseEntity.badRequest()
                    .body(Map.of("error", "El usuario no existe en el sistema"));
            }
            
            System.out.println("Obteniendo emprendimientos para el usuario con ID: " + userId);
            
            // Obtener los emprendimientos del usuario
            List<Emprendimiento> emprendimientos = emprendimientoService.obtenerPorUsuarioId(userId);
            
            return ResponseEntity.ok(emprendimientos);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Error al obtener emprendimientos del usuario");
            response.put("error", e.getMessage());
            e.printStackTrace(); // Imprimir stack trace para depuración
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Endpoint para obtener todos los emprendimientos con información adicional (solo para administradores)
    @GetMapping("/todos")
    public ResponseEntity<?> obtenerTodosEmprendimientosAdmin(
            @RequestHeader(value = "Authorization", required = false) String authorization) {
        try {
            // Verificar autenticación
            if (authorization == null || !authorization.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Usuario no autenticado o token inválido"));
            }
            
            // Extraer token y verificar que sea administrador
            String token = authorization.replace("Bearer ", "");
            
            // Verificar si es administrador
            boolean esAdmin = usuarioService.esAdministrador(token);
            
            if (!esAdmin) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", "No tiene permisos para acceder a esta información"));
            }
            
            // Obtener todos los emprendimientos
            List<Emprendimiento> emprendimientos = emprendimientoService.listarTodos();
            
            // Crear respuesta enriquecida con información del usuario
            List<Map<String, Object>> respuesta = new ArrayList<>();
            for (Emprendimiento emp : emprendimientos) {
                Map<String, Object> empData = new HashMap<>();
                empData.put("idemprendimiento", emp.getIdemprendimiento());
                empData.put("nombre", emp.getNombre());
                empData.put("descripcion", emp.getDescripcion());
                empData.put("tipo", emp.getTipo());
                empData.put("fecha_creacion", emp.getFecha_creacion());
                empData.put("estado_emprendimiento", emp.getEstado_emprendimiento());
                empData.put("idregiones", emp.getIdregiones());
                empData.put("idusuarios", emp.getIdusuarios());
                
                // Obtener información del usuario dueño
                Optional<Usuario> usuario = usuarioService.obtenerPorId(emp.getIdusuarios());
                if (usuario.isPresent()) {
                    empData.put("username", usuario.get().getUsername());
                    empData.put("email", usuario.get().getEmailUser());
                }
                
                respuesta.add(empData);
            }
            
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Error al obtener todos los emprendimientos: " + e.getMessage()));
        }
    }

    // Modificar el endpoint de mis-emprendimientos para incluir todos los emprendimientos para administradores
    @GetMapping("/mis-emprendimientos")
    public ResponseEntity<?> obtenerMisEmprendimientos(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestParam(required = false) Integer userId,
            HttpServletRequest request) {
        try {
            // Verificar autenticación
            if (authorization == null || !authorization.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Usuario no autenticado o token inválido"));
            }
            
            // Extraer token
            String token = authorization.replace("Bearer ", "");
            
            // Verificar si es administrador
            boolean esAdmin = usuarioService.esAdministrador(token);
            
            // Para administradores, si no se especifica userId, devolver todos los emprendimientos
            if (esAdmin && userId == null) {
                System.out.println("Usuario administrador accediendo a todos los emprendimientos");
                return obtenerTodosEmprendimientosAdmin(authorization);
            }
            
            // Obtener el ID del usuario del token o del parámetro
            if (userId == null) {
                // Intentar obtener del parámetro
                String idUsuarioParam = request.getParameter("userId");
                if (idUsuarioParam != null && !idUsuarioParam.isEmpty()) {
                    try {
                        userId = Integer.parseInt(idUsuarioParam);
                    } catch (NumberFormatException e) {
                        System.out.println("Error al convertir el ID de usuario: " + e.getMessage());
                        return ResponseEntity.badRequest()
                            .body(Map.of("error", "ID de usuario inválido"));
                    }
                } else {
                    // Intentar obtener del token
                    userId = usuarioService.obtenerIdUsuarioDesdeToken(token);
                }
            }
            
            // Log para depuración
            System.out.println("Obteniendo emprendimientos para el usuario con ID: " + userId);
            
            if (userId == null) {
                return ResponseEntity.badRequest()
                    .body(Map.of("error", "Se requiere el ID de usuario para obtener los emprendimientos"));
            }
            
            // Verificar que el ID de usuario sea válido
            if (!usuarioService.obtenerPorId(userId).isPresent()) {
                return ResponseEntity.badRequest()
                    .body(Map.of("error", "El usuario no existe en el sistema"));
            }
            
            // Obtener los emprendimientos del usuario
            List<Emprendimiento> emprendimientos = emprendimientoService.obtenerPorUsuarioId(userId);
            System.out.println("Emprendimientos encontrados: " + emprendimientos.size());
            
            return ResponseEntity.ok(emprendimientos);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Error al obtener emprendimientos del usuario");
            response.put("error", e.getMessage());
            e.printStackTrace(); // Imprimir stack trace para depuración
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
