package com.example.proyectotalentotech.services;
import com.example.proyectotalentotech.model.Usuario;
import com.example.proyectotalentotech.security.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.proyectotalentotech.repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;

/**
 * Servicio para gestionar operaciones relacionadas con los usuarios.
 * <p>
 * Esta clase proporciona métodos para realizar operaciones CRUD sobre usuarios,
 * así como funcionalidades específicas relacionadas con la autenticación y 
 * verificación de roles mediante tokens JWT.
 * </p>
 * 
 * @author Equipo RedComunitaria
 * @version 1.0
 * @since 2023-03-30
 */
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;

    /**
     * Constructor para la inyección de dependencias.
     * 
     * @param usuarioRepository Repositorio para acceder a los datos de usuarios
     * @param jwtService Servicio para manejar operaciones con tokens JWT
     */
    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.jwtService = jwtService;
    }

    /**
     * Obtiene una lista de todos los usuarios registrados en el sistema.
     * 
     * @return Lista de todos los usuarios
     */
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }
    
    /**
     * Busca un usuario por su identificador único.
     * 
     * @param id El identificador del usuario a buscar
     * @return Un Optional que contiene el usuario si se encuentra, o vacío si no existe
     */
    public Optional<Usuario> obtenerPorId(Integer id) {
        return usuarioRepository.findById(id);
    }
    
    /**
     * Guarda o actualiza un usuario en la base de datos.
     * 
     * @param usuario El objeto Usuario a guardar o actualizar
     * @return El usuario guardado con su identificador asignado si es nuevo
     */
    @Transactional
    public Usuario guardar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }
    
    /**
     * Elimina un usuario del sistema por su identificador.
     * 
     * @param id El identificador del usuario a eliminar
     * @throws RuntimeException Si el usuario no existe
     */
    @Transactional
    public void eliminar(Integer id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
        } else {
            throw new RuntimeException("El usuario con ID " + id + " no existe.");
        }
    }
    
    /**
     * Busca un usuario por su dirección de correo electrónico.
     * 
     * @param email La dirección de correo electrónico a buscar
     * @return Un Optional que contiene el usuario si se encuentra, o vacío si no existe
     */
    public Optional<Usuario> obtenerPorEmail(String email) {
        return usuarioRepository.findByEmailUser(email);
    }
    
    /**
     * Verifica si un usuario es administrador según su token
     * 
     * @param token El token JWT del usuario
     * @return true si el usuario es administrador, false en caso contrario
     */
    public boolean esAdministrador(String token) {
        try {
            // Extraer el username del token
            String username = jwtService.extractUsername(token);
            if (username == null) {
                System.out.println("No se pudo extraer el username del token");
                return false;
            }
            
            // Buscar el usuario en la base de datos
            Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername(username);
            if (usuarioOpt.isEmpty()) {
                System.out.println("Usuario no encontrado: " + username);
                return false;
            }
            
            Usuario usuario = usuarioOpt.get();
            
            // Verificar si tiene rol de administrador
            if (usuario.getRol() != null && usuario.getRol().getNombreRol() != null) {
                return usuario.getRol().getNombreRol().equals("ROLE_ADMIN");
            }
            
            return false;
        } catch (Exception e) {
            System.out.println("Error al verificar si el usuario es administrador: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Obtiene el ID del usuario desde su token JWT
     * 
     * @param token El token JWT del usuario
     * @return El ID del usuario, o null si no se pudo obtener
     */
    public Integer obtenerIdUsuarioDesdeToken(String token) {
        try {
            // Extraer el username del token
            String username = jwtService.extractUsername(token);
            if (username == null) {
                System.out.println("No se pudo extraer el username del token");
                return null;
            }
            
            // Buscar el usuario en la base de datos
            Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername(username);
            if (usuarioOpt.isEmpty()) {
                System.out.println("Usuario no encontrado: " + username);
                return null;
            }
            
            return usuarioOpt.get().getIdusuarios();
        } catch (JwtException e) {
            System.out.println("Error al analizar el token JWT: " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.out.println("Error al obtener ID de usuario desde token: " + e.getMessage());
            return null;
        }
    }
}