package app.killacode.back_app.dto;

import app.killacode.back_app.model.Nivel;

public record NivelRequest(String id_nivel, boolean tipo, String ejercicio, String teoria, String tema) {

    public Nivel convertirToNivel() {
        return new Nivel(
                id_nivel,
                tipo,
                null,  //Esto para luego buscarlo
                null,
                null
        );
    }
    
}
