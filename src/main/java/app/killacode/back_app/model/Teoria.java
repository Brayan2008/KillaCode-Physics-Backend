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

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
public class Teoria {

    @Id
    private String id_teoria;

    @Column
    private String titulo;

    @OneToMany(mappedBy = "teoria", cascade = CascadeType.REMOVE)
    private final List<Seccion> secciones = new ArrayList<>();

}
