package com.example.proyectotalentotech.repository;

import com.example.proyectotalentotech.model.ComentariosYCalificaciones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComentariosYCalificacionesRepository extends JpaRepository<ComentariosYCalificaciones, Integer> {

}

