package app.killacode.back_app.dto;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import app.killacode.back_app.model.Ejercicio;

public record EjercicioResponse(String id, int puntos, int nivel, String texto, String respuesta, String malas) {

    public static EjercicioResponse conversionEjercicio(Ejercicio ejercicio) {
        String baseUri = ServletUriComponentsBuilder
                        .fromCurrentContextPath()
                        .build()
                        .toUriString();
        String malasUrl = baseUri + "/ejercicio/" + ejercicio.getId_ejercicio() + "/malas";

        return new EjercicioResponse(
            ejercicio.getId_ejercicio(),
            ejercicio.getPuntos(),
            ejercicio.getNivel(),
            ejercicio.getTexto(),
            ejercicio.getRespuesta(),
            malasUrl
        );
    }

}
