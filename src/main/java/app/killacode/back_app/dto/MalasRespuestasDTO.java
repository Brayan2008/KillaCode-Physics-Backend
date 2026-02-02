package app.killacode.back_app.dto;

import app.killacode.back_app.model.MalasRespuestas;
import io.swagger.v3.oas.annotations.media.Schema;

public record MalasRespuestasDTO(

        @Schema(description = "ID único de la mala respuesta", example = "mal1") String mal,

        @Schema(description = "ID del ejercicio relacionado con la mala respuesta", example = "ej01") String id_ejercicio

) {

    // Salida GET
    public static MalasRespuestasResponse conversionMalas(MalasRespuestas malas) {
        return new MalasRespuestasResponse(
                malas.getId_mal(),
                malas.getMala_respuesta(),
                malas.getEjercicio() != null ? malas.getEjercicio().getId_ejercicio() : null);
    }

    // Para guardar request
    public MalasRespuestas toMalasRespuestas() {
        return new MalasRespuestas(null, mal, null); // id: null, asi jpa generara un valor, ejercicio: null, en
                                                     // ejercicioRepository lo buscaremos
    }
}