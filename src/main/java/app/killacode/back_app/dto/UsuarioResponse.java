package app.killacode.back_app.dto;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import app.killacode.back_app.model.Usuario;

public record UsuarioResponse(Long id, String nombre, String correo, Integer edad, int puntaje,
        LocalDate fecha_registro, List<TemaResponse> temas, String puntuacionesUrl) {

    public static UsuarioResponse fromUsuario(Usuario u) {
     
        String url = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .build()
                .toUriString();

        return new UsuarioResponse(
                u.getId_usuario(),
                u.getNombre(),
                u.getCorreo(),
                u.getEdad() == 0 ? null : u.getEdad(),
                u.getPuntaje(),
                u.getFecha_registro(),
                u.getTemas().stream().map(TemaResponse::convertirTema).toList(),
               url + "/puntuaciones/usuario/" + u.getId_usuario());
    }

}
