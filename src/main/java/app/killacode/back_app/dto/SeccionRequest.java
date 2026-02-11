package app.killacode.back_app.dto;

import app.killacode.back_app.model.Seccion;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Solicitud para crear o actualizar una sección")
public record SeccionRequest(
    @Schema(description = "ID único de la sección", example = "sec1")
    String id, 
    @Schema(description = "Encabezado de la sección", example = "Introducción")
    String header, 
    @Schema(description = "Cuerpo de la sección", example = "Contenido explicativo...")
    String body, 
    @Schema(description = "Pie de página de la sección", example = "Nota importante...")
    String footer, 
    @Schema(description = "Posición de la imagen en la sección", example = "1")
    int posicion, 
    @Schema(description = "URL de la imagen asociada", example = "http://example.com/imagen.png")
    String imagen,
    @Schema(description = "ID de la teoría a la que pertenece", example = "teoria1")
    String id_teoria) {

    public Seccion toSeccion() {
        return new Seccion(id, header, body, footer, posicion, imagen, null);
    }

}
