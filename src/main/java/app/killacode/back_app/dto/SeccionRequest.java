package app.killacode.back_app.dto;

import app.killacode.back_app.model.Seccion;

public record SeccionRequest(String id, String header, String body, String footer, int posicion, String imagen,
        String id_teoria) {

    public Seccion toSeccion() {
        return new Seccion(id, header, body, footer, posicion, imagen, null);
    }

}
