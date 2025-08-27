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

@RestController
@RequestMapping("/tema")
public class TemaController {

    @Autowired
    private TemaService temaService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getTema(@PathVariable String id) {
        return temaService.get(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

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

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> updateTema(@PathVariable String id, @RequestBody TemaRequest tema) {
        return temaService.update(id, Optional.of(tema))
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTema(@PathVariable String id) {
        return temaService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

}
