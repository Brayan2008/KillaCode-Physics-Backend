package app.killacode.back_app.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Nivel {

    @Id
    private String id_nivel;
    
    @Column
    private boolean tipo;
    
    @OneToOne //Unidireccionales porque solo se puede acceder desde el nivel y no al reves (por ahora)
    @JoinColumn(name = "id_practica")
    private Practica practica;
    
    @OneToOne
    @JoinColumn(name = "id_teoria")
    private Teoria teoria;

    @ManyToOne
    @JoinColumn(name = "id_tema")
    private Tema tema;    
}

