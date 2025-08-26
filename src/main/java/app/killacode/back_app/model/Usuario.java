package app.killacode.back_app.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Getter;

@Entity
@Getter
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id_usuario;

    @Column(nullable = false)
    private String correo;

    @Column
    private String password;

    @Column(nullable = false)
    private String nombre;

    @Column
    private int edad;

    @Column(nullable = false)
    private LocalDate fecha_registro;

    @Column
    private int puntaje;

    @OneToMany(mappedBy = "id_usuario")
    private List<Puntuacion_Semanal> puntuaciones;

    @ManyToMany
    @JoinTable(name = "TemasxUsuario", joinColumns = @JoinColumn(name = "id_usuario"), inverseJoinColumns = @JoinColumn(name = "id_tema"))
    private final List<Tema> temas = new ArrayList<>();

}
