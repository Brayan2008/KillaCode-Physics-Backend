package app.killacode.back_app.dto;

import java.time.LocalDate;

import app.killacode.back_app.model.Usuario;

// El campo password puede ser null pero debe estar presente en el request
public record UsuarioRequest(String correo, String password, String nombre, int edad) {

    public Usuario toUser() {
        return new Usuario(null, correo, password, nombre, edad, LocalDate.now(), 0);
    }

}
