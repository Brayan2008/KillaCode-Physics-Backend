package app.killacode.back_app.controller;

import org.springframework.web.bind.annotation.RestController;

import app.killacode.back_app.dto.PuntuacionRequest;
import app.killacode.back_app.service.interfaces.PuntuacionService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/puntuaciones")
public class PuntuacionController {

    @Autowired
    private PuntuacionService puntuacionService;

    @GetMapping("/usuario/{id}")
    public ResponseEntity<?> getByUsuario(@PathVariable long id) {
        return puntuacionService.findByUsuario(id).isPresent()
                ? ResponseEntity.ok(puntuacionService.findByUsuario(id).get())
                : ResponseEntity.notFound().build();
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> updatePuntuacion(@PathVariable long id, @RequestBody PuntuacionRequest puntuacion) {
        return puntuacionService.update(id, Optional.of(puntuacion))
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePuntuacion(@PathVariable Long id) {
        return puntuacionService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

}
