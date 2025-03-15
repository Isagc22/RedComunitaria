package model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "USUARIOS")
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUSUARIOS;

    @Column(nullable = false, length = 185, unique = true)
    private String email_user;

    @Column(nullable = false, length = 15)
    private String password_user;

    @Column(nullable = false)
    public boolean estado_user;


}
