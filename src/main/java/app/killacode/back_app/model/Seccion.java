package app.killacode.back_app.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Builder
@Getter
public class Seccion {
    
    @Id
    private String id_section;

    @Column
    private String header;

    @Column
    private String body;

    @Column
    private String footer;

    @Column
    private Integer posicion;

    @Column
    private String image;

    @ManyToOne
    @JoinColumn(name = "id_teoria") 
    private Teoria teoria; //FK   
}