package app.killacode.back_app.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import app.killacode.back_app.dto.UsuarioDTORequest.*;
import app.killacode.back_app.dto.UsuarioResponse;
import app.killacode.back_app.service.UsuarioServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Usuarios", description = "Operaciones CRUD para los usuarios")
@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioServiceImpl usuarioService;

    @Operation(summary = "Obtener un usuario por su ID", description = "Operacion para obtener los datos principales de un usuario a partir del ID")
    @ApiResponse(responseCode = "200", description = "Usuario encontrando", content = {
            @Content(schema = @Schema(implementation = UsuarioResponse.class)) })
    @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content)
    @GetMapping("/{id}")
    public ResponseEntity<?> getUsuario(@PathVariable long id) {
        return usuarioService.get(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear un nuevo usuario en la app", description = "Crea adszzqa<q un nuevo usuario a partir del email, nombre, contraseña y edad")
    @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente", content = @Content)
    @ApiResponse(responseCode = "400", description = "Error con el formato JSON del usuario a registrar.", content = @Content)
    @ApiResponse(responseCode = "500", description = "Error del servidor al procesar la petición.", content = @Content)
    @PostMapping("/crear")
    public ResponseEntity<?> postUsuario(@Valid @RequestBody UsuarioRequest usuario) {
        var created = usuarioService.create(Optional.of(usuario));
        if (created.isPresent()) {
            var uri = ServletUriComponentsBuilder.fromCurrentRequest() // TODO arreglar el error, debe referenciar al
                                                                       // GET request de usuarios
                    .path("/{id}")
                    .buildAndExpand(created.get().id())
                    .toUri();
            return ResponseEntity.created(uri).build();
        }
        return ResponseEntity.badRequest().build();
    }

    @Operation(summary = "Actualizar un usuario registrado", description = "Actualiza los datos del usuario existente utilizando su *ID*. Solo se puede cambiar la contraseña, el nombre y el correo")
    @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente.", content = @Content)
    @ApiResponse(responseCode = "400", description = "Error con el formato JSON del usuario a actualizar.", content = @Content)
    @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content)
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> updateUsuario(@PathVariable long id, @Valid @RequestBody UsuarioUpdateRequest usuario) {
        return usuarioService.update(id, Optional.of(usuario))
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Asignar un tema a un usuario", description = "Los temas (*que se componen de teoría y ejercicios*), pueden ser asigandos a cada usuario, para ello se requieren solo de dos parametros, el **ID del tema *(temaId)*** y el **ID del usuario *(id)***")
    @ApiResponse(responseCode = "200", description = "Ejercicio asignado exitosamente", content = @Content)
    @ApiResponse(responseCode = "404", description = "**Usuario o ejercicio** no encontrado", content = @Content)
    @PostMapping("/{id}/asignarTema/{temaId}")
    public ResponseEntity<?> asignarTema(@PathVariable long id, @PathVariable String temaId) {
        return usuarioService.asignarTema(id, temaId)
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }

    // TODO realizar un borrado logico en la base de datos, agregando un campo
    // boolean adicional a la entidad usuario
    @Operation(summary = "Elimina un ejercicio por su ID", description = "Elimina un usuario de la aplicación", deprecated = true)
    @ApiResponse(responseCode = "204", description = "Usuario eliminado exitosamente.", content = @Content)
    @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUsuario(@PathVariable long id) {
        return usuarioService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

}