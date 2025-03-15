package model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "PRODUCCION_CONSUMO_ENERGIA")
@Data
public class ProduccionConsumoEnergia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPRODUCCION_CONSUMO_ENERGIA;




}
