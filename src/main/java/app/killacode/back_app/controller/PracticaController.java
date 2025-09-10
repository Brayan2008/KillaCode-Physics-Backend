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



@RestController
@RequestMapping("practica")
public class PracticaController {

    @Autowired
    private PracticaService practicaService;

    @Autowired
    private EjercicioService ejercicioService;

    @GetMapping("/{id}/ejercicios")
    public ResponseEntity<List<EjercicioResponse>> getEjercicios(@PathVariable String id) {
            if (ejercicioService.getEjercicios(id).isPresent()) {
                return ResponseEntity.ok(ejercicioService.getEjercicios(id).get());
            }
            return ResponseEntity.notFound().build();
    } 
    
    @PostMapping()
    public ResponseEntity<Void> postPractica(@RequestBody Practica practica) {
        if (practicaService.crearPractica(practica)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
    

}