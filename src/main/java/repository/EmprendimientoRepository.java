package repository;

import model.Emprendimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmprendimientoRepository extends JpaRepository<Emprendimiento, Integer> {
}
