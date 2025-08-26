package app.killacode.back_app.dto;

import app.killacode.back_app.model.MalasRespuestas;

public record MalasRespuestasRequest(Long id, String mal, String id_ejericio) {

    public MalasRespuestas toMalasRespuestas() {
        return new MalasRespuestas(id,mal,null);
    }
}
