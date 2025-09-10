package app.killacode.back_app.dto;

import java.util.List;

import app.killacode.back_app.model.Ejercicio;
import app.killacode.back_app.model.MalasRespuestas;

public record EjercicioRequest(String id, String practicaId,int puntos, int nivel, String texto, String respuesta, List<String> malas, String retroalimentacion, String imagen) {
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
