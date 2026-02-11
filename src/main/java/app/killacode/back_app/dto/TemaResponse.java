package app.killacode.back_app.dto;

import java.util.List;

import app.killacode.back_app.model.Tema;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Respuesta con la información de un tema")
public record TemaResponse(
    @Schema(description = "ID único del tema", example = "tema1")
    String id_tema, 
    @Schema(description = "Título del tema", example = "Cinemática")
    String titulo, 
    @Schema(description = "Lista de niveles asociados al tema")
    List<NivelResponse> niveles) {

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
