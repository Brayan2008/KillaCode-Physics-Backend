package app.killacode.back_app.dto;

import app.killacode.back_app.model.Seccion;

public record SeccionResponse(String id, String header, String body, String footer, Integer posicion, String imagen) {

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
