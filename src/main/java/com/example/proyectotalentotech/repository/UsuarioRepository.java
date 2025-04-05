package com.example.proyectotalentotech.repository;

import com.example.proyectotalentotech.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Repositorio para acceder a los datos de los usuarios.
 * <p>
 * Esta interfaz proporciona métodos para realizar operaciones CRUD en la entidad Usuario,
 * así como consultas personalizadas para recuperar usuarios por email y nombre de usuario.
 * </p>
 * 
 * @author Equipo RedComunitaria
 * @version 1.0
 * @since 2023-03-30
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    
    /**
     * Busca un usuario por su dirección de correo electrónico.
     * 
     * @param email La dirección de correo electrónico a buscar
     * @return Un Optional que contiene el usuario si se encuentra, o vacío si no existe
     */
    Optional<Usuario> findByEmailUser(String email);
    
    /**
     * Busca un usuario por su nombre de usuario.
     * 
     * @param username El nombre de usuario a buscar
     * @return Un Optional que contiene el usuario si se encuentra, o vacío si no existe
     */
    Optional<Usuario> findByUsername(String username);
}

