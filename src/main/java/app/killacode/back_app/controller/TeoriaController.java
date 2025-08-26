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

@RestController
@RequestMapping("/teoria")
public class TeoriaController {

    @Autowired
    private TeoriaService teoriaService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getTeoria(@PathVariable String id) {
        return teoriaService.get(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/secciones")
    public ResponseEntity<?> getSecciones(@PathVariable String id) {
        return teoriaService.getSecciones(id).isPresent()
                ? ResponseEntity.ok(teoriaService.getSecciones(id))
                : ResponseEntity.notFound().build();
    }

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

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> updateTeoria(@PathVariable String id, @RequestBody Teoria teoria) {
        return teoriaService.update(id, Optional.of(teoria))
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTeoria(@PathVariable String id) {
        return teoriaService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

}
