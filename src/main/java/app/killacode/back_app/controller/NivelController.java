package app.killacode.back_app.controller;

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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import app.killacode.back_app.dto.NivelRequest;
import app.killacode.back_app.dto.NivelResponse;
import app.killacode.back_app.service.interfaces.NivelService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Niveles", description = "Operaciones CRUD para los niveles")
@ApiResponse(responseCode = "404", description = "Nivel no encontrado", content = @Content)
@RestController
@RequestMapping("/nivel")
public class NivelController {

    @Autowired
    private NivelService nivelService;

    @Operation(summary = "Obtener un nivel por su ID", description = "Devuelve un nivel específico por su ID")
    @ApiResponse(responseCode = "200", description = "Nivel encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<NivelResponse> getNivel(@PathVariable String id) {
        return nivelService.get(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear un nuevo nivel", description = "Crea un nuevo nivel con los datos proporcionados")
    @ApiResponse(responseCode = "201", description = "Nivel creado", content = @Content)
    @ApiResponse(responseCode = "400", description = "Error en el formato del nivel", content = @Content)
    @PostMapping
    public ResponseEntity<NivelResponse> createNivel(@RequestBody NivelRequest nivelRequest) {
        Map<Boolean, Optional<NivelResponse>> createdNivelMap = nivelService.create(Optional.of(nivelRequest));
        if (createdNivelMap.containsKey(true)) {

            var uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(createdNivelMap.get(true).get().id())
                    .toUri();

            return ResponseEntity.created(uri).build();
        }
        return ResponseEntity.badRequest().build();
    }

    @Operation(summary = "Actualizar un nivel", description = "Actualiza un nivel existente por su ID")
    @ApiResponse(responseCode = "204", description = "Nivel actualizado", content = @Content)
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateNivel(@PathVariable String id, @RequestBody NivelRequest nivelRequest) {
        return nivelService.update(id, Optional.of(nivelRequest))
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Eliminar un nivel", description = "Elimina un nivel por su ID")
    @ApiResponse(responseCode = "204", description = "Nivel eliminado", content = @Content)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNivel(@PathVariable String id) {
        return nivelService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

}
