package app.killacode.back_app.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Tema {
    
    @Id
    private String id_tema;

    @Column
    private String nombre_tema;

    @OneToMany(mappedBy = "tema", cascade = CascadeType.ALL)
    private final List<Nivel> niveles = new ArrayList<>();

    @ManyToMany(mappedBy = "temas")
    private final List<Usuario> usuarios = new ArrayList<>();
}
