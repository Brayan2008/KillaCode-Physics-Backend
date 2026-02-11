package app.killacode.back_app.dto;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import app.killacode.back_app.model.Dias;
import app.killacode.back_app.model.Puntuacion_Semanal;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Respuesta con la información de una puntuación")
public record PuntuacionResponse(
    @Schema(description = "ID único de la puntuación", example = "1")
    Long id, 
    @Schema(description = "Día de la semana", example = "LUNES")
    Dias dias, 
    @Schema(description = "Puntos acumulados", example = "50")
    int puntos, 
    @Schema(description = "URL del usuario asociado", example = "http://localhost:8080/usuario/1")
    String usuarioUrl) {

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
