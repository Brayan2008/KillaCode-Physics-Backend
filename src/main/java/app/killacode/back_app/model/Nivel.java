package app.killacode.back_app.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Getter;

@Entity
@Getter
public class Nivel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id_nivel;
    
    @Column
    private boolean tipo;
    
    @OneToOne //Unidireccionales porque solo se puede acceder desde el nivel y no al reves (por ahora)
    @JoinColumn(name = "id_ejercicio")
    private Ejercicio ejercicio;
    
    @OneToOne
    @JoinColumn(name = "id_teoria")
    private Teoria teoria;

    @ManyToOne
    @JoinColumn(name = "id_tema")
    private Tema tema;    
}

