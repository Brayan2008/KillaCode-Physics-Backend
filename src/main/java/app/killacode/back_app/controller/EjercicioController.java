package app.killacode.back_app.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import app.killacode.back_app.model.Ejercicio;
import app.killacode.back_app.service.interfaces.EjercicioService;

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

@RestController
@RequestMapping("/ejercicio")
public class EjercicioController {

    @Autowired
    private EjercicioService ejercicioService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getEjercicio(@PathVariable String id) {
        return ejercicioService.get(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/malas")
    public ResponseEntity<?> getMalas(@PathVariable String id) {
        return ejercicioService.getMalas(id).isPresent()
                ? ResponseEntity.ok(ejercicioService.getMalas(id))
                : ResponseEntity.notFound().build();
    }

    @PostMapping("/crear")
    public ResponseEntity<?> postEjercicio(@RequestBody Ejercicio ejercicio) {
        if (ejercicioService.create(Optional.of(ejercicio))) {

            var uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(ejercicio.getId_ejercicio())
                    .toUri();

            return ResponseEntity.created(uri).build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> updateEjercicio(@PathVariable String id, @RequestBody Ejercicio ejercicio) {
        return ejercicioService.update(id, Optional.of(ejercicio))
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEjercicio(@PathVariable String id) {
        return ejercicioService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

}
