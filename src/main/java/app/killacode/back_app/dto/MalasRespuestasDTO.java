package app.killacode.back_app.dto;

import app.killacode.back_app.model.MalasRespuestas;

public record MalasRespuestasDTO(String mal, String id_ejercicio) {

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