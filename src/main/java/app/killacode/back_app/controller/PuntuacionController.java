package app.killacode.back_app.controller;

import org.springframework.web.bind.annotation.RestController;

import app.killacode.back_app.dto.PuntuacionRequest;
import app.killacode.back_app.service.interfaces.PuntuacionService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
@Tag(name = "Puntuaciones", description = "Operaciones sobre las puntuaciones de usuarios")
@ApiResponse(responseCode = "404", description = "Recurso no encontrado", content = @Content)
@RestController
@RequestMapping("/puntuaciones")
public class PuntuacionController {

    @Autowired
    private PuntuacionService puntuacionService;

    @Operation(summary = "Obtener puntuaciones por usuario", description = "Devuelve las puntuaciones asociadas a un usuario por su ID")
    @ApiResponse(responseCode = "200", description = "Puntuaciones encontradas", content = @Content(array = @ArraySchema(schema = @Schema(implementation = PuntuacionRequest.class))))
    @GetMapping("/usuario/{id}")
    public ResponseEntity<?> getByUsuario(@PathVariable long id) {
        return puntuacionService.findByUsuario(id).isPresent()
                ? ResponseEntity.ok(puntuacionService.findByUsuario(id).get())
                : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Actualizar una puntuación", description = "Actualiza una puntuación de un usuario a través de la ID del usuario.  Solo se puede actualizar la puntuación y la fecha. Siendo la fecha un enum con los valores: **LUNES, MARTES, MIERCOLES, JUEVES, VIERNES, SABADO, DOMINGO**")
    @ApiResponse(responseCode = "200", description = "Puntuación actualizada", content = @Content)
    @ApiResponse(responseCode = "404", description = "Formato no valido, revisar que la fecha o la puntuacion cumplan con el formato establecido", content = @Content)
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> updatePuntuacion(@PathVariable long id, @RequestBody PuntuacionRequest puntuacion) {
        return puntuacionService.update(id, Optional.of(puntuacion))
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }
}
