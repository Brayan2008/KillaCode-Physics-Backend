package app.killacode.back_app.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Practica {

    @Id
    private String id_practica;
    
    @OneToMany(mappedBy = "practica", cascade = CascadeType.ALL)
    private final List<Ejercicio> ejercicios = new ArrayList<>();
    
}
