package app.killacode.back_app.dto;

import app.killacode.back_app.model.Seccion;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Respuesta con la información de una sección")
public record SeccionResponse(
    @Schema(description = "ID único de la sección", example = "sec1")
    String id, 
    @Schema(description = "Encabezado de la sección", example = "Introducción")
    String header, 
    @Schema(description = "Cuerpo de la sección", example = "Contenido explicativo...")
    String body, 
    @Schema(description = "Pie de página de la sección", example = "Nota importante...")
    String footer, 
    @Schema(description = "Posición de la sección en la secuencia", example = "1")
    Integer posicion, 
    @Schema(description = "URL de la imagen asociada", example = "http://example.com/imagen.png")
    String imagen) {

    public static SeccionResponse fromSeccion(Seccion seccion) {
        return new SeccionResponse(
                seccion.getId_section(),
                seccion.getHeader(),
                seccion.getBody(),
                seccion.getFooter(),
                seccion.getPosicion(),
                seccion.getImage());
    }
}
