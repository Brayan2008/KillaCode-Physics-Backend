package app.killacode.back_app.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MalasRespuestas {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_mal;

    @Column
    private String mala_respuesta;

    @ManyToOne
    @JoinColumn(name = "id_ejercicio")
    private Ejercicio ejercicio;
}
