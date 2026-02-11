package app.killacode.back_app.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.killacode.back_app.dto.EjercicioDTO.*;
import app.killacode.back_app.dto.EjercicioResponse;
import app.killacode.back_app.model.Ejercicio;
import app.killacode.back_app.repository.EjercicioRepository;
import app.killacode.back_app.repository.MalasRespuestasRepository;
import app.killacode.back_app.repository.PracticaRepository;
import app.killacode.back_app.service.interfaces.EjercicioService;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class EjercicioServiceImpl implements EjercicioService {

    @Autowired
    private EjercicioRepository ejercicioRepository;

    @Autowired
    private PracticaRepository practicaRepository;

    @Override
    public Optional<EjercicioResponse> get(String id) {
        return ejercicioRepository.findById(id).map(EjercicioResponse::conversionEjercicio);
    }

    @Override
    public Map<Boolean, Optional<Ejercicio>> create(Optional<EjercicioRequest> obj) {
        if (obj.isPresent()) {
            var ejercicio = obj.get().toEjercicio();
            var id = ejercicio.getId_ejercicio();
            // permitir crear si no existe id (lo genera automaticamente) o si no existe
            if ((id == null || !ejercicioRepository.existsById(id))
                    && practicaRepository.existsById(obj.get().practicaId())) {
                var practica = practicaRepository.findById(obj.get().practicaId()).get();
                ejercicio.setPractica(practica);
                ejercicioRepository.save(ejercicio);
                return Map.of(true, Optional.of(ejercicio));
            }
        }
        return Map.of(false, Optional.empty()); // Map no acepta null
    }

    @Override
    public boolean update(String id, Optional<EjercicioUpdateRequest> obj) throws RuntimeException {
        if (obj.isPresent() && ejercicioRepository.existsById(id)) {
            var ej = obj.get();
            var ejercicio = new Ejercicio(id, ej.puntos(), ej.nivel(), ej.texto(), ej.respuesta(),
                    ej.retroalimentacion(), ej.imagen(), null);
            // Buscames esa nueva practica y se la asignamos, si no existe lanza una
            // excepcion
            practicaRepository.findById(ej.practicaId()).ifPresentOrElse(ejercicio::setPractica, () -> {
                throw new RuntimeException("La practica no existe");
            });
            ejercicioRepository.save(ejercicio);
            return true;

        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        if (ejercicioRepository.existsById(id)) {
            ejercicioRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Optional<List<EjercicioResponse>> getEjercicios(String idPractica) {
        return practicaRepository.existsById(idPractica) ? Optional.ofNullable(
                practicaRepository.findById(idPractica).get().getEjercicios()
                        .stream()
                        .map(EjercicioResponse::conversionEjercicio)
                        .toList())
                : Optional.empty();
    }

}
