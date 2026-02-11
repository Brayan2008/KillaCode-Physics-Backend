package app.killacode.back_app.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import app.killacode.back_app.dto.EjercicioDTO.*;
import app.killacode.back_app.dto.EjercicioResponse;
import app.killacode.back_app.model.Ejercicio;
import app.killacode.back_app.service.interfaces.EjercicioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Map;
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

@Tag(name = "Ejercicios", description = "Operaciones CRUD para los ejercicios")
@RestController
@RequestMapping("/ejercicio")
@ApiResponse(responseCode = "404", description = "Ejercicio no encontrado", content = @Content)
public class EjercicioController {

    @Autowired
    private EjercicioService ejercicioService;

    @Operation(summary = "Obtener un ejercicio por su ID", description = "Devuelve un ejercicio específico utilizando su **ID único**.")
    @ApiResponse(responseCode = "200", description = "Ejercicio encontrado.", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = EjercicioResponse.class))
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getEjercicio(@PathVariable String id) {
        return ejercicioService.get(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear un nuevo ejercicio", description = "Crea un nuevo ejercicio con los datos proporcionados.")
    @ApiResponse(responseCode = "201", description = "Ejercicio creado exitosamente.", content = @Content)
    @ApiResponse(responseCode = "400", description = "Error en el formato del ejercicio.", content = @Content)
    @ApiResponse(responseCode = "500", description = "Error del servidor al procesar la petición.", content = @Content)
    @PostMapping("/crear")
    public ResponseEntity<?> postEjercicio(@RequestBody EjercicioRequest ejercicio) {
        Map<Boolean, Optional<Ejercicio>> createdEjercicioMap = ejercicioService.create(Optional.of(ejercicio));
        if (createdEjercicioMap.containsKey(true)) {
            var uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(createdEjercicioMap.get(true).get().getId_ejercicio())
                    .toUri();

            return ResponseEntity.created(uri).build();
        }
        return ResponseEntity.badRequest().build();
    }

    @Operation(summary = "Actualizar un ejercicio existente por su ID", description = "Actualiza los datos de un ejercicio existente utilizando su *ID*.")
    @ApiResponse(responseCode = "400", description = "Error en el formato del ejercicio.", content = @Content)
    @ApiResponse(responseCode = "200", description = "Ejercicio actualizado exitosamente.", content = @Content)
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> updateEjercicio(@PathVariable String id, @RequestBody EjercicioUpdateRequest ejercicio) {
        try {
            return ejercicioService.update(id, Optional.of(ejercicio))
                    ? ResponseEntity.ok().build()
                    : ResponseEntity.notFound().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // TODO revisar que se puedan eliminar ejercicios que esten relacionados con
    // otros (REVISAR)
    @Operation(summary = "Elimina un ejercicio por su ID", description = "Elimina un ejercicio, eliminando tambien las malas respuestas, en cuanto a la practica relacionada, solo se elimina la relacion, pero no la practica en si.")
    @ApiResponse(responseCode = "204", description = "Ejercicio eliminado exitosamente.", content = @Content)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEjercicio(@PathVariable String id) {
        return ejercicioService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

}