package app.killacode.back_app.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record MalasRespuestasResponse(
    @Schema(description = "ID único de la mala respuesta", example = "1")
    Long id, 
    @Schema(description = "Texto de la mala respuesta", example = "\"10 m/s²\"")
    String mal, 
    @Schema(description = "ID del ejercicio asociado", example = "ej13")
    String id_ejercicio) {
}
