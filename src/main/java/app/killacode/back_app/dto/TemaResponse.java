package app.killacode.back_app.dto;

import java.util.List;

import app.killacode.back_app.model.Tema;

public record TemaResponse(String id_tema, String titulo, List<NivelResponse> niveles) {

    public static TemaResponse convertirTema(Tema tema){

        return new TemaResponse(
            tema.getId_tema(),
            tema.getNombre_tema(),
            tema.getNiveles().stream()
                .map(NivelResponse::convertirNivel)
                .toList()
        );
    }

}
