package app.killacode.back_app.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import app.killacode.back_app.dto.MalasRespuestasDTO;
import app.killacode.back_app.model.MalasRespuestas;
import app.killacode.back_app.service.interfaces.MalasRespService;

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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "MalasRespuestas", description = "Operaciones CRUD para respuestas incorrectas")
@ApiResponse(responseCode = "404", description = "Recurso no encontrado", content = @Content)
@RestController
@RequestMapping("/malas")
public class MalasRespuestasController {

    @Autowired
    private MalasRespService malasService;

    @Operation(summary = "Obtener malas respuestas por ID", description = "Devuelve las malas respuestas asociadas a un ejercicio por su ID")
    @ApiResponse(responseCode = "200", description = "Malas respuestas encontradas", content = @Content)
    @GetMapping("/{id}")
    public ResponseEntity<?> getMalas(@PathVariable long id) {
        return malasService.get(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear malas respuestas", description = "Crea las respuestas incorrectas asociadas a un ejercicio")
    @ApiResponse(responseCode = "201", description = "Malas respuestas creadas", content = @Content)
    @ApiResponse(responseCode = "400", description = "Error en el formato de las malas respuestas", content = @Content)
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody MalasRespuestasDTO malas) {
        Map<Boolean, Optional<MalasRespuestas>> responseMap = malasService.create(Optional.of(malas));
       
        if (responseMap.containsKey(true)) {
            var uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(responseMap.get(true).get().getId_mal())
                    .toUri();

            return ResponseEntity.created(uri).build();
        }
       
        return ResponseEntity.badRequest().build();
    }

    @Operation(summary = "Actualizar malas respuestas", description = "Actualiza las respuestas incorrectas por su ID")
    @ApiResponse(responseCode = "200", description = "Malas respuestas actualizadas", content = @Content)
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody MalasRespuestasDTO malas) {
        if (malasService.update(id, Optional.of(malas))) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Eliminar malas respuestas", description = "Elimina las malas respuestas por su ID")
    @ApiResponse(responseCode = "204", description = "Malas respuestas eliminadas", content = @Content)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        boolean deleted = malasService.delete(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}