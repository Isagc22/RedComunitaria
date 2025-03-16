package repository;

import model.ComentariosYCalificaciones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComentariosYCalificacionesRepository extends JpaRepository<ComentariosYCalificaciones, Integer> {
}

