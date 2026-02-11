package app.killacode.back_app.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import app.killacode.back_app.dto.SeccionRequest;
import app.killacode.back_app.dto.SeccionResponse;
import app.killacode.back_app.service.interfaces.SeccionService;

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
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Secciones", description = "Operaciones CRUD para las secciones")
@ApiResponse(responseCode = "404", description = "Sección no encontrada", content = @Content)
@RestController
@RequestMapping("/seccion")
public class SeccionController {

    @Autowired
    private SeccionService seccionService;

    @Operation(summary = "Obtener una sección por ID", description = "Devuelve una sección específica por su ID")
    @ApiResponse(responseCode = "200", description = "Sección encontrada", content = @Content(schema = @Schema(implementation = SeccionResponse.class)))
    @GetMapping("/{id}")
    public ResponseEntity<?> getSeccion(@PathVariable String id) {
        return seccionService.get(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear una sección", description = "Crea una nueva sección con los datos proporcionados")
    @ApiResponse(responseCode = "201", description = "Sección creada", content = @Content)
    @ApiResponse(responseCode = "400", description = "Error en el formato de la sección", content = @Content)
    @PostMapping("/crear")
    public ResponseEntity<?> create(@RequestBody SeccionRequest seccion) {
        if (seccionService.create(Optional.of(seccion))) {

            var uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(seccion.id())
                    .toUri();
                    
            return ResponseEntity.created(uri).build();
        }
        return ResponseEntity.badRequest().build();
    }

    @Operation(summary = "Actualizar una sección", description = "Actualiza los datos de una sección existente por su ID")
    @ApiResponse(responseCode = "200", description = "Sección actualizada", content = @Content)
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody SeccionRequest seccion) {
        boolean updated = seccionService.update(id, Optional.of(seccion));
        if (updated) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Eliminar una sección", description = "Elimina una sección por su ID")
    @ApiResponse(responseCode = "204", description = "Sección eliminada", content = @Content)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        boolean deleted = seccionService.delete(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}