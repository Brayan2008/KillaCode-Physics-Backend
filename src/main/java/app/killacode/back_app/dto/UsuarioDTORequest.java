package app.killacode.back_app.dto;

import java.time.LocalDate;

import app.killacode.back_app.model.Usuario;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UsuarioDTORequest {
    // El campo password puede ser null pero debe estar presente en el request
    @Schema(description = "Solicitud para crear un usuario")
    public record UsuarioRequest(
            @Schema(example = "usuario@example.com") @Email String correo,
            @Schema(example = "password123") String password,
            @Schema(example = "Juan Perez") @NotBlank String nombre,
            @Schema(example = "25") int edad) {

        public Usuario toUser() {
            return new Usuario(null, correo, password, nombre, edad, LocalDate.now(), 0);
        }

    }
    @Schema(description = "Solicitud para actualizar un usuario")
    public record UsuarioUpdateRequest(
            @Schema(example = "usuario@example.com") @Email String correo,
            @Schema(example = "password123") String password,
            @Schema(example = "Juan Perez") @NotBlank String nombre) {
    }
}
