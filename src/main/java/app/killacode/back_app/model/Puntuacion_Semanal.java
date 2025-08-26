package app.killacode.back_app.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Entity
@Getter
public class Puntuacion_Semanal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id_puntacion; 

    @Enumerated(EnumType.STRING)
    @Column(unique = true)
    private Dias dias;

    @Column
    private int puntos;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario id_usuario;
}

enum Dias {
    DOMINGO,
    LUNES,
    MARTES,
    MIERCOLES,
    JUEVES,
    VIERNES,
    SABADO
}
