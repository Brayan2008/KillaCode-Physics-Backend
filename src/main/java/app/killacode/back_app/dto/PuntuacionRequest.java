package app.killacode.back_app.dto;

import app.killacode.back_app.model.Dias;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Solicitud para registrar una puntuación")
public record PuntuacionRequest(
        @Schema(description = "Día de la semana", example = "LUNES") Dias dia,
        @Schema(description = "Puntos obtenidos", example = "50") int puntos) {
}
