package app.killacode.back_app.dto;

import java.util.List;

import app.killacode.back_app.model.Ejercicio;

public record EjercicioResponse(String id, int puntos, int nivel, String texto, String respuesta, String retroalimentacion, List<MalasRespuestasResponse> malas, String imagen) {

    public static EjercicioResponse conversionEjercicio(Ejercicio ejercicio) {
        return new EjercicioResponse(
            ejercicio.getId_ejercicio(),
            ejercicio.getPuntos(),
            ejercicio.getNivel(),
            ejercicio.getTexto(),
            ejercicio.getRespuesta(),
            ejercicio.getRetroalimentacion(),
            ejercicio.getLista_malas().stream().map(MalasRespuestasDTO::conversionMalas).toList(),
            ejercicio.getImagen()
        );
    }
}
