package app.killacode.back_app.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import app.killacode.back_app.model.Teoria;
import app.killacode.back_app.service.interfaces.TeoriaService;

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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Teorías", description = "Operaciones CRUD para la teoría")
@ApiResponse(responseCode = "404", description = "Recurso no encontrado", content = @Content)
@RestController
@RequestMapping("/teoria")
public class TeoriaController {

    @Autowired
    private TeoriaService teoriaService;

    @Operation(summary = "Obtener teoría por ID", description = "Devuelve la teoría correspondiente a un tema por su ID")
    @ApiResponse(responseCode = "200", description = "Teoría encontrada", content = @Content)
    @GetMapping("/{id}")
    public ResponseEntity<?> getTeoria(@PathVariable String id) {
        return teoriaService.get(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Obtener secciones de teoría", description = "Devuelve las secciones de una teoría por su ID")
    @ApiResponse(responseCode = "200", description = "Secciones encontradas", content = @Content)
    @GetMapping("/{id}/secciones")
    public ResponseEntity<?> getSecciones(@PathVariable String id) {
        return teoriaService.getSecciones(id).isPresent()
                ? ResponseEntity.ok(teoriaService.getSecciones(id))
                : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Crear teoría", description = "Crea una nueva entrada de teoría")
    @ApiResponse(responseCode = "201", description = "Teoría creada", content = @Content)
    @ApiResponse(responseCode = "400", description = "Error en el formato de la teoría", content = @Content)
    @PostMapping("/crear")
    public ResponseEntity<?> postTeoria(@RequestBody Teoria teoria) {
        if (teoriaService.create(Optional.of(teoria))) {

            var uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(teoria.getId_teoria())
                    .toUri();

            return ResponseEntity.created(uri).build();
        }
        return ResponseEntity.badRequest().build();
    }

    @Operation(summary = "Actualizar teoría", description = "Actualiza una entrada de teoría por su ID")
    @ApiResponse(responseCode = "200", description = "Teoría actualizada", content = @Content)
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> updateTeoria(@PathVariable String id, @RequestBody Teoria teoria) {
        return teoriaService.update(id, Optional.of(teoria))
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Eliminar teoría", description = "Elimina una entrada de teoría por su ID")
    @ApiResponse(responseCode = "204", description = "Teoría eliminada", content = @Content)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTeoria(@PathVariable String id) {
        return teoriaService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

}
