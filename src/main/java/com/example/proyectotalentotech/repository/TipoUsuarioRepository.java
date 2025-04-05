package com.example.proyectotalentotech.repository;

import com.example.proyectotalentotech.model.TipoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la entidad TipoUsuario.
 * <p>
 * Esta interfaz proporciona métodos para acceder y manipular datos de tipos de usuario
 * en la base de datos, incluyendo operaciones CRUD básicas heredadas de JpaRepository
 * sin implementar consultas personalizadas adicionales.
 * </p>
 * <p>
 * Los tipos de usuario definen las diferentes categorías de usuarios que pueden existir
 * en el sistema, como administradores, usuarios comunes, consultores, etc., y son
 * fundamentales para la gestión de permisos y accesos dentro de la aplicación.
 * </p>
 * 
 * @author Equipo RedComunitaria
 * @version 1.0
 * @since 2023-03-30
 */
@Repository
public interface TipoUsuarioRepository extends JpaRepository<TipoUsuario, Integer> {
}

