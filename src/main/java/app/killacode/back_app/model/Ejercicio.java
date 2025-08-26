package app.killacode.back_app.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Ejercicio {

    @Id
    private String id_ejercicio;

    @Column
    private int puntos;
    
    @Column
    private int nivel;
    
    @Column
    private String texto;
    
    @Column
    private String respuesta;

    @OneToMany(mappedBy = "ejercicio")
    private final List<MalasRespuestas> lista_malas = new ArrayList<>();

}
