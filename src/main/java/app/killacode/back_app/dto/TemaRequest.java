package app.killacode.back_app.dto;

import java.util.List;

import app.killacode.back_app.model.Tema;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Solicitud para crear o actualizar un tema")
public record TemaRequest(
    @Schema(description = "ID único del tema", example = "tema1")
    String id_tema, 
    @Schema(description = "Título del tema", example = "Cinemática")
    String titulo, 
    @Schema(description = "Lista de IDs de niveles asociados", example = "[\"nivel1\", \"nivel2\"]")
    List<String> niveles) {

    public Tema toTema() {
        return new Tema(id_tema, titulo);
    }

}
