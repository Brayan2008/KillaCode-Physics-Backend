package app.killacode.back_app.dto;

import java.util.List;

import app.killacode.back_app.model.Teoria;

public record TeoriaResponse(String id, String title, List<SeccionResponse> secciones) {

    public static TeoriaResponse conversionTeoria(Teoria teoria) {
        List<SeccionResponse> seccionesResponse = teoria.getSecciones().stream()
                .map(SeccionResponse::fromSeccion)
                .toList();
        return new TeoriaResponse(teoria.getId_teoria(),
                teoria.getTitulo(),
                seccionesResponse);
    }

}
