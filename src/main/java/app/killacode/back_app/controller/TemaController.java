package app.killacode.back_app.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import app.killacode.back_app.dto.TemaRequest;
import app.killacode.back_app.dto.TemaResponse;
import app.killacode.back_app.model.Tema;
import app.killacode.back_app.service.interfaces.TemaService;

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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Temas", description = "Operaciones CRUD para los temas")
@ApiResponse(responseCode = "404", description = "Tema no encontrado", content = @Content)
@RestController
@RequestMapping("/tema")
public class TemaController {

    @Autowired
    private TemaService temaService;

    @Operation(summary = "Obtener un tema por su ID", description = "Devuelve un tema específico utilizando su ID")
    @ApiResponse(responseCode = "200", description = "Tema encontrado", content = @Content(schema = @Schema(implementation = TemaResponse.class)))
    @GetMapping("/{id}")
    public ResponseEntity<?> getTema(@PathVariable String id) {
        return temaService.get(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear un nuevo tema", description = "Crea un nuevo tema con los datos proporcionados")
    @ApiResponse(responseCode = "201", description = "Tema creado exitosamente", content = @Content)
    @ApiResponse(responseCode = "400", description = "Error en el formato del tema", content = @Content)
    @PostMapping("/crear")
    public ResponseEntity<?> postTema(@RequestBody TemaRequest tema) {
        Map<Boolean, Optional<Tema>> createdTemaMap = temaService.create(Optional.of(tema));
        if (createdTemaMap.containsKey(true)) {

            var uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(createdTemaMap.get(true).get().getId_tema())
                    .toUri();

            return ResponseEntity.created(uri).build();
        }
        return ResponseEntity.badRequest().build();
    }

    @Operation(summary = "Actualizar un tema", description = "Actualiza un tema existente por su ID")
    @ApiResponse(responseCode = "200", description = "Tema actualizado exitosamente", content = @Content)
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> updateTema(@PathVariable String id, @RequestBody TemaRequest tema) {
        return temaService.update(id, Optional.of(tema))
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Eliminar un tema", description = "Elimina un tema por su ID")
    @ApiResponse(responseCode = "204", description = "Tema eliminado exitosamente", content = @Content)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTema(@PathVariable String id) {
        return temaService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

}
