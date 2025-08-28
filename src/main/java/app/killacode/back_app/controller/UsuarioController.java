package app.killacode.back_app.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import app.killacode.back_app.dto.UsuarioRequest;
import app.killacode.back_app.service.UsuarioServiceImpl;

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
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioServiceImpl usuarioService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getUsuario(@PathVariable long id) {
        return usuarioService.get(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/crear")
    public ResponseEntity<?> postUsuario(@RequestBody UsuarioRequest usuario) {
        var created = usuarioService.create(Optional.of(usuario));
        if (created.isPresent()) {
            var uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(created.get().id())
                    .toUri();
            return ResponseEntity.created(uri).build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> updateUsuario(@PathVariable long id, @RequestBody UsuarioRequest usuario) {
        return usuarioService.update(id, Optional.of(usuario))
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUsuario(@PathVariable long id) {
        return usuarioService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

}
