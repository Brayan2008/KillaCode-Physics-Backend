package app.killacode.back_app.dto;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import app.killacode.back_app.model.Nivel;

public record NivelResponse(String id, boolean tipo, String url_practica, String url_teoria, String tema) {

    public static NivelResponse convertirNivel(Nivel nivel) {

        boolean hayTeoria = nivel.getTeoria() != null;
        boolean hayTema = nivel.getTema() != null;
        boolean hayEjercicios = nivel.getPractica() != null;

        String url = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .build()
                .toUriString();

        String teoriaUrl = hayTeoria ? url + "/teoria/" + nivel.getTeoria().getId_teoria() : null;
        String ejercicioUrl = hayEjercicios ? url + "/practica/" + nivel.getPractica().getId_practica() + "/ejercicios" : null;

        return new NivelResponse(
                nivel.getId_nivel(),
                nivel.isTipo(),
                hayEjercicios ? ejercicioUrl : null,
                hayTeoria ? teoriaUrl : null,
                hayTema ? nivel.getTema().getId_tema() : null);
    }

}
