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

@RestController
@RequestMapping("/malas")
public class MalasRespuestasController {

    @Autowired
    private MalasRespService malasService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getMalas(@PathVariable long id) {
        return malasService.get(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

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

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody MalasRespuestasDTO malas) {
        if (malasService.update(id, Optional.of(malas))) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        boolean deleted = malasService.delete(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}