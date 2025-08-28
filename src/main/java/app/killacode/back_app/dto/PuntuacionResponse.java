package app.killacode.back_app.dto;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import app.killacode.back_app.model.Dias;
import app.killacode.back_app.model.Puntuacion_Semanal;

public record PuntuacionResponse(Long id, Dias dias, int puntos, String usuarioUrl) {

    public static PuntuacionResponse fromModel(Puntuacion_Semanal p){
                String url = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .build()
                .toUriString();

        return new PuntuacionResponse(
            p.getId_puntacion(),
            p.getDias(),
            p.getPuntos(),
            url + "/usuario/" + p.getId_usuario().getId_usuario()
        );
    }

}
