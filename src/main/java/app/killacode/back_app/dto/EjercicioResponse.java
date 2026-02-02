package app.killacode.back_app.dto;

import java.util.List;

import app.killacode.back_app.model.Ejercicio;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Respuesta que representa un ejercicio")
public record EjercicioResponse(

        @Schema(description = "ID único del ejercicio", example = "ej13") String id,

        @Schema(description = "Puntos asignados al ejercicio", example = "10") int puntos,

        @Schema(description = "Nivel de dificultad del ejercicio", example = "2") int nivel,

        @Schema(description = "Texto del ejercicio", example = "¿Cúal es la aceleración de la gravedad?") String texto,

        @Schema(description = "Respuesta correcta del ejercicio", example = "\"9.8 m/s²\"") String respuesta,

        @Schema(description = "Retroalimentación para el ejercicio", example = "\"2Aunque muchos lo aproximan a 10 m/s², la aceleración real es 9.8m/s²\"") String retroalimentacion,

        @Schema(description = "Lista de respuestas incorrectas", exampleClasses = MalasRespuestasDTO.class) List<MalasRespuestasResponse> malas,

        @Schema(description = "URL de la imagen asociada al ejercicio", example = "http://example.com/imagen.png") String imagen

) {

    public static EjercicioResponse conversionEjercicio(Ejercicio ejercicio) {
        return new EjercicioResponse(
                ejercicio.getId_ejercicio(),
                ejercicio.getPuntos(),
                ejercicio.getNivel(),
                ejercicio.getTexto(),
                ejercicio.getRespuesta(),
                ejercicio.getRetroalimentacion(),
                ejercicio.getLista_malas().stream().map(MalasRespuestasDTO::conversionMalas).toList(),
                ejercicio.getImagen());
    }
}
