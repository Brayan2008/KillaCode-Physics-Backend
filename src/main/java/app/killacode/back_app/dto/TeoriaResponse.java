package app.killacode.back_app.dto;

import java.util.List;

import app.killacode.back_app.model.Teoria;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Respuesta con la información de una teoría")
public record TeoriaResponse(
    @Schema(description = "ID único de la teoría", example = "teoria1")
    String id, 
    @Schema(description = "Título de la teoría", example = "Conceptos Básicos")
    String title, 
    @Schema(description = "Lista de secciones que componen la teoría")
    List<SeccionResponse> secciones) {

    public static TeoriaResponse conversionTeoria(Teoria teoria) {
        List<SeccionResponse> seccionesResponse = teoria.getSecciones().stream()
                .map(SeccionResponse::fromSeccion)
                .toList();
        return new TeoriaResponse(teoria.getId_teoria(),
                teoria.getTitulo(),
                seccionesResponse);
    }

}
