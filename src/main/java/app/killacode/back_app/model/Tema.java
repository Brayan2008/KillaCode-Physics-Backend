package app.killacode.back_app.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Getter;

@Entity
@Getter
public class Tema {
    
    @Id
    @GeneratedValue
    private String id_tema;

    @Column
    private String nombre_tema;

    @OneToMany(mappedBy = "tema")
    private List<Nivel> niveles;

    @ManyToMany(mappedBy = "temas")
    private final List<Usuario> usuarios = new ArrayList<>();
}
