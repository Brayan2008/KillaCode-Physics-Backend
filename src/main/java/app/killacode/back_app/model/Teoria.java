package app.killacode.back_app.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
 
import io.swagger.v3.oas.annotations.media.Schema;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Schema(name = "Teoria", description = "Entidad que representa una teoría con su título y sus secciones")
public class Teoria {

    @Id
    @Schema(description = "Identificador único de la teoría", example = "te01")
    private String id_teoria;

    @Column
    @Schema(description = "Título de la teoría", example = "Leyes de Newton")
    private String titulo;

    @OneToMany(mappedBy = "teoria", cascade = CascadeType.REMOVE)
    @Schema(hidden = true)
    private final List<Seccion> secciones = new ArrayList<>();

}
