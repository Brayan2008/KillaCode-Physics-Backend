package app.killacode.back_app.dto;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import app.killacode.back_app.model.Nivel;

public record NivelResponse(String id, boolean tipo, String url_ejercicio, String url_teoria, String tema) {

    public static NivelResponse convertirNivel(Nivel nivel) {

        boolean hayTeoria = nivel.getTeoria() != null;
        boolean hayTema = nivel.getTema() != null;
        boolean hayEjercicio = nivel.getEjercicio() != null;


        String url = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .build()
                .toUriString();

        String teoriaUrl = hayTeoria ? url + "/teoria/" + nivel.getTeoria().getId_teoria() : null;
        String ejercicioUrl = hayEjercicio ? url + "/ejercicio/" + nivel.getEjercicio().getId_ejercicio() : null;

        return new NivelResponse(
                nivel.getId_nivel(),
                nivel.isTipo(),
                hayEjercicio ? ejercicioUrl: null,
                hayTeoria ? teoriaUrl : null,
                hayTema ? nivel.getTema().getId_tema() : null);
    }

}
