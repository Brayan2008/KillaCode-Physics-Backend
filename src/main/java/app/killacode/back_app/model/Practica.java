package app.killacode.back_app.model;

import java.util.ArrayList;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "Modelo de una práctica")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Practica {

    @Schema(description = "ID único de la práctica", example = "p01")
    @Id
    private String id_practica;

    //Este atributo no se expone en la API
    @Schema(hidden = true)
    @OneToMany(mappedBy = "practica", cascade = CascadeType.ALL)
    private final List<Ejercicio> ejercicios = new ArrayList<>();
    
}
