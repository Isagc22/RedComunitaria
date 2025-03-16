package repository;

import model.DatosPersonales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DatosPersonalesRepository extends JpaRepository<DatosPersonales, Integer> {
}

