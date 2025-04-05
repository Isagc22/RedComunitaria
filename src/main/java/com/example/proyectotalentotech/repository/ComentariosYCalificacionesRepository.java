package com.example.proyectotalentotech.repository;

import com.example.proyectotalentotech.model.ComentariosYCalificaciones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la entidad ComentariosYCalificaciones.
 * <p>
 * Esta interfaz proporciona métodos para acceder y manipular datos de comentarios
 * y calificaciones en la base de datos, incluyendo operaciones CRUD básicas heredadas
 * de JpaRepository sin implementar consultas personalizadas adicionales.
 * </p>
 * <p>
 * Los comentarios y calificaciones representan las opiniones y evaluaciones que los
 * usuarios realizan sobre los emprendimientos, permitiendo retroalimentación y
 * valoración de los productos o servicios ofrecidos.
 * </p>
 * 
 * @author Equipo RedComunitaria
 * @version 1.0
 * @since 2023-03-30
 */
@Repository
public interface ComentariosYCalificacionesRepository extends JpaRepository<ComentariosYCalificaciones, Integer> {

}

