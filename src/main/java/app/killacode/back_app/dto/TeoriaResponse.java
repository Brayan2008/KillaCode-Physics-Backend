package app.killacode.back_app.dto;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import app.killacode.back_app.model.Teoria;

public record TeoriaResponse(String id, String title, String secciones) {

    public static TeoriaResponse conversionTeoria(Teoria teoria) {
        String baseUri = ServletUriComponentsBuilder
                        .fromCurrentContextPath()
                        .build()
                        .toUriString();
        String seccionesUrl = baseUri + "/teoria/" + teoria.getId_teoria() + "/secciones";
        
        return new TeoriaResponse(teoria.getId_teoria(),
                      teoria.getTitulo(),
                      seccionesUrl);
    }

}
