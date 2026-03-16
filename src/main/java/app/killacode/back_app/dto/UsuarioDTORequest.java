package app.killacode.back_app.dto;

import java.time.LocalDate;

import app.killacode.back_app.model.Usuario;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * 
 * DTOs para manejar las solicitudes de creación y actualización de
 * usuarios. Contiene dos registros:
 * <ul>
 * <li>{@link UsuarioRequest}: Para crear un nuevo usuario, con campos <b> correo,
 * password, nombre y edad.</b> El método {@code toUser()} convierte esta solicitud
 * en una entidad Usuario.</li>
 * 
 * <li>{@link UsuarioUpdateRequest}: Para actualizar un usuario existente, con
 * campos <b>correo, password y nombre.</b> No incluye edad ni fecha</li>
 * </ul>
 * 
 * @author bmaster
 * @see UsuarioServiceImpl
 */
public class UsuarioDTORequest {
    // El campo password puede ser null pero debe estar presente en el request
    @Schema(description = "Solicitud para crear un usuario")
    public record UsuarioRequest(
            @Schema(example = "usuario@example.com") @Email String correo,
            @Schema(example = "password123") String password,
            @Schema(example = "Juan Perez") @NotBlank String nombre,
            @Schema(example = "25") int edad) {

        public Usuario toUser() {
            return new Usuario(null, correo, null, nombre, edad, LocalDate.now(), 0);
        }

    }

    @Schema(description = "Solicitud para actualizar un usuario")
    public record UsuarioUpdateRequest(
            @Schema(example = "usuario@example.com") @Email String correo,
            @Schema(example = "password123") String password,
            @Schema(example = "Juan Perez") @NotBlank String nombre) {
    }
}
