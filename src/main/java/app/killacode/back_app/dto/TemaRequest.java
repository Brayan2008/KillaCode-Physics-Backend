package app.killacode.back_app.dto;

import java.util.List;

import app.killacode.back_app.model.Tema;

public record TemaRequest(String id_tema, String titulo, List<String> niveles) {

    public Tema toTema() {
        return new Tema(id_tema, titulo);
    }

}
