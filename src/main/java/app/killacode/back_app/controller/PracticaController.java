package app.killacode.back_app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.killacode.back_app.dto.EjercicioResponse;
import app.killacode.back_app.model.Practica;
import app.killacode.back_app.service.interfaces.EjercicioService;
import app.killacode.back_app.service.interfaces.PracticaService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Prácticas", description = "Operaciones relacionadas con prácticas y sus ejercicios")
@ApiResponse(responseCode = "404", description = "Recurso no encontrado", content = @Content)
@RestController
@RequestMapping("practica")
public class PracticaController {

    @Autowired
    private PracticaService practicaService;

    @Autowired
    private EjercicioService ejercicioService;

    @GetMapping("/{id}/ejercicios")
    @Operation(summary = "Obtener ejercicios de una práctica", description = "Devuelve la lista de ejercicios asociados a una práctica por su ID")
    @ApiResponse(responseCode = "200", description = "Lista de ejercicios encontrada", content = @Content(array = @ArraySchema(schema = @Schema(implementation = EjercicioResponse.class))))
    public ResponseEntity<List<EjercicioResponse>> getEjercicios(@PathVariable String id) {
            if (ejercicioService.getEjercicios(id).isPresent()) {
                return ResponseEntity.ok(ejercicioService.getEjercicios(id).get());
            }
            return ResponseEntity.notFound().build();
    }
    
    @Operation(summary = "Crear una práctica", description = "Crea una práctica nueva con los datos proporcionados")
    @ApiResponse(responseCode = "200", description = "Práctica creada exitosamente", content = @Content)
    @ApiResponse(responseCode = "400", description = "Error en el formato de la práctica", content = @Content)
    @PostMapping()
    public ResponseEntity<Void> postPractica(@RequestBody Practica practica) {
        if (practicaService.crearPractica(practica)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
    

}