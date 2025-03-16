package repository;

import model.ProduccionConsumoEnergia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProduccionConsumoEnergiaRepository extends JpaRepository<ProduccionConsumoEnergia, Integer> {
}
