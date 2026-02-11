package app.killacode.back_app.dto;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import app.killacode.back_app.model.Usuario;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Respuesta con la información del usuario")
public record UsuarioResponse(
    @Schema(description = "ID único del usuario", example = "1")
    Long id, 
    @Schema(description = "Nombre completo del usuario", example = "Juan Perez")
    String nombre, 
    @Schema(description = "Correo electrónico del usuario", example = "usuario@example.com")
    String correo, 
    @Schema(description = "Edad del usuario", example = "25")
    Integer edad, 
    @Schema(description = "Puntaje total del usuario", example = "150")
    int puntaje,
    @Schema(description = "Fecha de registro del usuario", example = "2023-01-01")
    LocalDate fecha_registro, 
    @Schema(description = "Lista de temas progresados por el usuario")
    List<TemaResponse> temas, 
    @Schema(description = "URL para acceder a las puntuaciones del usuario", example = "http://localhost:8080/puntuaciones/usuario/1")
    String puntuacionesUrl) {

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
