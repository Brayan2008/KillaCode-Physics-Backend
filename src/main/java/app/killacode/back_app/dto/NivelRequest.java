package app.killacode.back_app.dto;

import app.killacode.back_app.model.Nivel;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Solicitud para crear o actualizar un nivel")
public record NivelRequest(
    @Schema(description = "ID único del nivel", example = "nivel1")
    String id_nivel, 
    @Schema(description = "Tipo de nivel (true: práctica, false: teoría)", example = "true")
    boolean tipo, 
    @Schema(description = "ID de la práctica asociada", example = "practica1")
    String practica, 
    @Schema(description = "ID de la teoría asociada", example = "teoria1")
    String teoria, 
    @Schema(description = "ID del tema al que pertenece el nivel", example = "tema1")
    String tema) {

    public Nivel convertirToNivel() {
        return new Nivel(
                id_nivel,
                tipo,
                null,  //Esto para luego buscarlo
                null,
                null
        );
    }
    
}
