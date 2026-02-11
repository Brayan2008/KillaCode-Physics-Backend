package app.killacode.back_app.dto;

import java.util.List;

import app.killacode.back_app.model.Ejercicio;
import app.killacode.back_app.model.MalasRespuestas;
import io.swagger.v3.oas.annotations.media.Schema;

public class EjercicioDTO {

    @Schema(description = "Solicitud para crear un ejercicio")
    public record EjercicioRequest(
            @Schema(description = "ID único del ejercicio", example = "ej01") String id,
            @Schema(description = "ID de la práctica asociada", example = "p01") String practicaId,
            @Schema(description = "Puntos asignados al ejercicio", example = "10") int puntos,
            @Schema(description = "Nivel de dificultad del ejercicio", example = "3") int nivel,
            @Schema(description = "Texto del ejercicio", example = "Una esfera esta cayendo libremente. ¿Cuál es su aceleración?") String texto,
            @Schema(description = "Respuesta correcta del ejercicio", example = "\"50 m/s²\"") String respuesta,
            @Schema(description = "Lista de respuestas incorrectas", example = "[\"10 m/s²\", \"0 m/s²\", \"9.8 m/s²\"]") List<String> malas,
            @Schema(description = "Retroalimentación para el ejercicio", example = "La aceleración de un objeto en caída libre cerca de la superficie de la Tierra es aproximadamente 9.8 m/s².") String retroalimentacion,
            @Schema(description = "URL de la imagen asociada al ejercicio", example = "http://example.com/imagen.png") String imagen) {

        public Ejercicio toEjercicio() {
            var b = new Ejercicio(id, puntos, nivel, texto, respuesta, retroalimentacion, imagen, null);
            if (malas != null)
                malas.forEach(mala -> {
                    MalasRespuestas mal = new MalasRespuestasDTO(mala, b.getId_ejercicio()).toMalasRespuestas();
                    mal.setEjercicio(b);
                    b.getLista_malas().add(mal);
                });
            return b;
        }

    }

    @Schema(description = "Solicitud para actualizar un ejercicio")
    public record EjercicioUpdateRequest(
            @Schema(description = "ID de la práctica asociada", example = "p01") String practicaId,
            @Schema(description = "Puntos asignados al ejercicio", example = "10") int puntos,
            @Schema(description = "Nivel de dificultad del ejercicio", example = "3") int nivel,
            @Schema(description = "Texto del ejercicio", example = "Una esfera esta cayendo libremente. ¿Cuál es su aceleración?") String texto,
            @Schema(description = "Respuesta correcta del ejercicio", example = "\"50 m/s²\"") String respuesta,
            @Schema(description = "Retroalimentación para el ejercicio", example = "La aceleración de un objeto en caída libre cerca de la superficie de la Tierra es aproximadamente 9.8 m/s².") String retroalimentacion,
            @Schema(description = "URL de la imagen asociada al ejercicio", example = "http://example.com/imagen.png") String imagen) {
    }
    
}