package com.example.proyectotalentotech.controller;

import com.example.proyectotalentotech.model.Usuario;
import com.example.proyectotalentotech.model.DatosPersonales;
import com.example.proyectotalentotech.repository.DatosPersonalesRepository;
import com.example.proyectotalentotech.services.UsuarioService;
import com.example.proyectotalentotech.services.DatosPersonalesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

/**
 * Controlador REST para gestionar operaciones relacionadas con los usuarios.
 * <p>
 * Esta clase proporciona endpoints para realizar operaciones CRUD sobre los usuarios
 * del sistema, así como funcionalidades específicas como el inicio de sesión.
 * </p>
 * 
 * @author Equipo RedComunitaria
 * @version 1.0
 * @since 2023-03-30
 */
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final DatosPersonalesService datosPersonalesService;
    private final DatosPersonalesRepository datosPersonalesRepository;

    /**
     * Constructor para la inyección de dependencias.
     * 
     * @param usuarioService Servicio para gestionar operaciones de usuarios
     * @param datosPersonalesService Servicio para gestionar operaciones de datos personales
     * @param datosPersonalesRepository Repositorio para acceder directamente a los datos personales
     */
    public UsuarioController(UsuarioService usuarioService, DatosPersonalesService datosPersonalesService, DatosPersonalesRepository datosPersonalesRepository) {
        this.usuarioService = usuarioService;
        this.datosPersonalesService = datosPersonalesService;
        this.datosPersonalesRepository = datosPersonalesRepository;
    }

    /**
     * Crea un nuevo usuario en el sistema.
     * 
     * @param usuario El objeto Usuario a crear
     * @return ResponseEntity con el usuario creado
     */
    @PostMapping
    public ResponseEntity<Usuario> crear(@RequestBody Usuario usuario) {
        return ResponseEntity.ok(usuarioService.guardar(usuario));
    }

    /**
     * Obtiene la lista de todos los usuarios registrados.
     * 
     * @return ResponseEntity con la lista de usuarios o un error si no hay usuarios
     */
    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodos() {
        List<Usuario> lista = usuarioService.listarTodos();
        if (lista.isEmpty()) {
            System.out.println("⚠️ La tabla de usuarios está vacía.");
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(lista);
    }

    /**
     * Busca un usuario por su identificador único.
     * 
     * @param id El identificador del usuario a buscar
     * @return ResponseEntity con el usuario si se encuentra, o notFound si no existe
     */
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerPorId(@PathVariable Integer id) {
        Optional<Usuario> entity = usuarioService.obtenerPorId(id);
        if (entity.isEmpty()) {
            System.out.println("No se encontró el usuario con id " + id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(entity.get());
    }

    /**
     * Actualiza los datos de un usuario existente.
     * 
     * @param id El identificador del usuario a actualizar
     * @param usuario El objeto Usuario con los nuevos datos
     * @return ResponseEntity con el usuario actualizado o notFound si no existe
     */
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizar(@PathVariable Integer id, @RequestBody Usuario usuario) {
        Optional<Usuario> entity = usuarioService.obtenerPorId(id);

        if (entity.isPresent()) {
            Usuario usuarioActual = entity.get();
            usuarioActual.setEmailUser(usuario.getEmailUser());
            usuarioActual.setPassword_user(usuario.getPassword_user());
            usuarioActual.setEstado_user(usuario.getEstado_user());
            return ResponseEntity.ok(usuarioService.guardar(usuarioActual));
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Elimina un usuario del sistema.
     * 
     * @param id El identificador del usuario a eliminar
     * @return ResponseEntity sin contenido si se eliminó correctamente, o badRequest si no existe
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (!usuarioService.obtenerPorId(id).isPresent()) {
            System.out.println("No se encontró el usuario con id " + id);
            return ResponseEntity.badRequest().build();
        }
        usuarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Procesa el inicio de sesión de un usuario.
     * <p>
     * Este método verifica las credenciales del usuario y, si son correctas,
     * devuelve información del usuario junto con sus datos personales.
     * </p>
     * 
     * @param usuario El objeto Usuario con las credenciales de inicio de sesión
     * @return ResponseEntity con los datos del usuario y sus datos personales si las credenciales son correctas,
     *         o un mensaje de error si son incorrectas
     */
    @PostMapping("/login")
    public ResponseEntity<?> iniciarSesion(@RequestBody Usuario usuario) {
        try {
            System.out.println("Iniciando sesión para usuario con email: " + usuario.getEmailUser());
            Optional<Usuario> usuarioExistente = usuarioService.obtenerPorEmail(usuario.getEmailUser());

            if (usuarioExistente.isEmpty() ||
                    !usuarioExistente.get().getPassword_user().equals(usuario.getPassword_user())) {
                System.out.println("Credenciales incorrectas para usuario: " + usuario.getEmailUser());
                return ResponseEntity.status(401).body("Credenciales incorrectas");
            }

            Usuario usuarioAutenticado = usuarioExistente.get();
            System.out.println("Usuario autenticado con ID: " + usuarioAutenticado.getIdusuarios());

            // Buscar datos personales 
            Optional<DatosPersonales> datosPersonales = datosPersonalesRepository.findByIdusuarios(usuarioAutenticado.getIdusuarios());

            if (datosPersonales.isEmpty()) {
                System.out.println("⚠️ No se encontraron datos personales para el usuario con ID " + usuarioAutenticado.getIdusuarios());
            } else {
                System.out.println("Datos personales encontrados para el usuario con ID " + usuarioAutenticado.getIdusuarios());
            }

            // Construir respuesta con usuario y datos personales
            Map<String, Object> response = new HashMap<>();
            response.put("usuario", usuarioAutenticado);
            response.put("datosPersonales", datosPersonales.orElse(null));

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("Error en inicio de sesión: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error en el servidor: " + e.getMessage());
        }
    }
}
