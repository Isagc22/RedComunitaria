package model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "REGIONES")
@Data
public class Regiones {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idREGIONES;

    private String nombre_Region;




}
