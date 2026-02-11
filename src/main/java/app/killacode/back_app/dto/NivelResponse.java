package app.killacode.back_app.dto;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import app.killacode.back_app.model.Nivel;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Respuesta con la información de un nivel")
public record NivelResponse(
    @Schema(description = "ID único del nivel", example = "nivel1")
    String id, 
    @Schema(description = "Tipo de nivel (true: desbloqueado, false: bloqueado)", example = "true")
    boolean tipo, 
    @Schema(description = "URL de la práctica asociada", example = "http://localhost:8080/practica/practica1/ejercicios")
    String url_practica, 
    @Schema(description = "URL de la teoría asociada", example = "http://localhost:8080/teoria/teoria1")
    String url_teoria, 
    @Schema(description = "ID del tema asociado", example = "tema1")
    String tema) {

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
