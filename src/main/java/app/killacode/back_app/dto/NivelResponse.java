package app.killacode.back_app.dto;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import app.killacode.back_app.model.Nivel;

public record NivelResponse(String id, boolean tipo, String url_ejercicio, String url_teoria, String tema) {

    public static NivelResponse convertirNivel(Nivel nivel) {

        String url = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .build()
                .toUriString();

        String teoriaUrl = url + "/teoria/" + nivel.getTeoria().getId_teoria();
        String ejercicioUrl = url + "/ejercicio/" + nivel.getEjercicio().getId_ejercicio();

        return new NivelResponse(
                nivel.getId_nivel(),
                nivel.isTipo(),
                nivel.getEjercicio() != null ? ejercicioUrl: null,
                nivel.getTeoria() != null ? teoriaUrl : null,
                nivel.getTema() != null ? nivel.getTema().getId_tema() : null);
    }

}
