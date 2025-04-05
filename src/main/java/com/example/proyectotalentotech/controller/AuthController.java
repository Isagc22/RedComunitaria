package com.example.proyectotalentotech.controller;

import com.example.proyectotalentotech.model.Roles;
import com.example.proyectotalentotech.model.Usuario;
import com.example.proyectotalentotech.repository.RolesRepository;
import com.example.proyectotalentotech.repository.UsuarioRepository;
import com.example.proyectotalentotech.security.JwtService;
import com.example.proyectotalentotech.security.dto.AuthRequestDTO;
import com.example.proyectotalentotech.security.dto.AuthResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * Controlador para la autenticación y registro de usuarios.
 * <p>
 * Este controlador proporciona endpoints para que los usuarios puedan iniciar sesión
 * y registrarse en el sistema. Maneja la autenticación mediante JWT (JSON Web Token) y
 * soporta diferentes mecanismos de verificación de contraseñas para mayor compatibilidad
 * con sistemas existentes.
 * </p>
 * <p>
 * Características principales:
 * <ul>
 *   <li>Autenticación de usuarios mediante nombre de usuario o correo electrónico</li>
 *   <li>Generación de tokens JWT para usuarios autenticados</li>
 *   <li>Registro de nuevos usuarios con asignación automática de roles</li>
 *   <li>Soporte para contraseñas en texto plano (compatibilidad) y encriptadas</li>
 * </ul>
 * </p>
 * 
 * @author Equipo RedComunitaria
 * @version 1.0
 * @since 2023-03-30
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final RolesRepository rolesRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    /**
     * Autentica a un usuario y genera un token JWT si las credenciales son válidas.
     * <p>
     * Este método recibe las credenciales del usuario (nombre de usuario/email y contraseña)
     * y verifica su validez. La autenticación se realiza de dos maneras:
     * <ol>
     *   <li>Verificación directa de contraseña (para contraseñas almacenadas en texto plano)</li>
     *   <li>Autenticación mediante Spring Security (para contraseñas encriptadas)</li>
     * </ol>
     * </p>
     * <p>
     * Si la autenticación es exitosa, el método genera un token JWT y lo devuelve junto con
     * información básica del usuario en el cuerpo de la respuesta.
     * </p>
     * 
     * @param request DTO que contiene las credenciales del usuario (username/email y password)
     * @return ResponseEntity con el token JWT y datos del usuario si la autenticación es exitosa,
     *         o un mensaje de error en caso contrario
     */
    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequestDTO request) {
        log.info("Intento de login con usuario: {}", request.getUsername());
        
        // Identificar si el usuario existe (por username o por email)
        Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername(request.getUsername());
        if (usuarioOpt.isEmpty()) {
            usuarioOpt = usuarioRepository.findByEmailUser(request.getUsername());
        }
        
        if (usuarioOpt.isEmpty()) {
            log.warn("Usuario no encontrado: {}", request.getUsername());
            return ResponseEntity.badRequest().body("Usuario no encontrado");
        }
        
        Usuario usuario = usuarioOpt.get();
        
        // Verificar la contraseña directamente (para compatibilidad con contraseñas antiguas)
        if (usuario.getPassword_user().equals(request.getPassword())) {
            log.info("Autenticación exitosa con contraseña en texto plano para: {}", request.getUsername());
            
            // Generar token JWT
            String jwtToken = jwtService.generateToken(usuario);
            
            // Si el usuario tiene un rol nulo, asignar rol de usuario por defecto
            if (usuario.getRol() == null) {
                Roles rolUsuario = rolesRepository.findById(2)
                        .orElseThrow(() -> new RuntimeException("Rol de usuario no encontrado"));
                usuario.setRol(rolUsuario);
                usuarioRepository.save(usuario);
            }
            
            return ResponseEntity.ok(
                AuthResponseDTO.builder()
                    .token(jwtToken)
                    .idUsuario(usuario.getIdusuarios())
                    .username(usuario.getUsername())
                    .rol(usuario.getRol() != null ? usuario.getRol().getNombreRol() : "ROLE_USER")
                    .build()
            );
        }
        
        // Intento de autenticación con Spring Security (para contraseñas encriptadas)
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            usuario.getUsername(),
                            request.getPassword()
                    )
            );
            
            if (authentication.isAuthenticated()) {
                log.info("Autenticación exitosa con Spring Security para: {}", request.getUsername());
                
                String jwtToken = jwtService.generateToken(usuario);
                
                return ResponseEntity.ok(
                    AuthResponseDTO.builder()
                        .token(jwtToken)
                        .idUsuario(usuario.getIdusuarios())
                        .username(usuario.getUsername())
                        .rol(usuario.getRol() != null ? usuario.getRol().getNombreRol() : "ROLE_USER")
                        .build()
                );
            }
        } catch (AuthenticationException e) {
            log.warn("Error de autenticación con Spring Security: {}", e.getMessage());
        }
        
        log.warn("Credenciales incorrectas para usuario: {}", request.getUsername());
        return ResponseEntity.badRequest().body("Credenciales incorrectas");
    }

    /**
     * Registra un nuevo usuario en el sistema.
     * <p>
     * Este método recibe las credenciales del nuevo usuario y verifica que no exista
     * un usuario con el mismo nombre de usuario o correo electrónico. Si las validaciones
     * son exitosas, crea un nuevo usuario en la base de datos, le asigna un rol predeterminado
     * y genera un token JWT para el usuario recién registrado.
     * </p>
     * <p>
     * El registro está simplificado y usa el username como email, y almacena la contraseña
     * en texto plano para mantener compatibilidad con sistemas existentes.
     * </p>
     * 
     * @param request DTO que contiene las credenciales del nuevo usuario (username y password)
     * @return ResponseEntity con el token JWT y datos del usuario si el registro es exitoso,
     *         o un mensaje de error en caso contrario
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequestDTO request) {
        log.info("Intento de registro de usuario: {}", request.getUsername());
        
        // Verificar si el usuario ya existe
        if(usuarioRepository.findByUsername(request.getUsername()).isPresent() ||
           usuarioRepository.findByEmailUser(request.getUsername()).isPresent()) {
            log.warn("El usuario ya existe: {}", request.getUsername());
            return ResponseEntity.badRequest().body("El usuario ya existe");
        }
        
        // Crear un nuevo usuario
        Usuario usuario = new Usuario();
        usuario.setUsername(request.getUsername());
        usuario.setEmailUser(request.getUsername()); // Usamos el username como email
        usuario.setPassword_user(request.getPassword()); // Guardamos la contraseña en texto plano para compatibilidad
        usuario.setEstado_user(true);
        
        // Asignar rol por defecto (usuario normal)
        Roles rolUsuario = rolesRepository.findById(2)
                .orElseThrow(() -> new RuntimeException("Rol de usuario no encontrado"));
        usuario.setRol(rolUsuario);
        
        // Guardar el usuario
        usuarioRepository.save(usuario);
        log.info("Usuario registrado exitosamente: {}", request.getUsername());
        
        // Generar token
        String jwtToken = jwtService.generateToken(usuario);
        
        return ResponseEntity.ok(
            AuthResponseDTO.builder()
                .token(jwtToken)
                .idUsuario(usuario.getIdusuarios())
                .username(usuario.getUsername())
                .rol(usuario.getRol().getNombreRol())
                .build()
        );
    }
} 