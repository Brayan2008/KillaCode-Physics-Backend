package app.killacode.back_app.service;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.killacode.back_app.dto.TemaRequest;
import app.killacode.back_app.dto.TemaResponse;
import app.killacode.back_app.model.Tema;
import app.killacode.back_app.repository.NivelRepository;
import app.killacode.back_app.repository.TemaRepository;
import app.killacode.back_app.service.interfaces.TemaService;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class TemaServiceImpl implements TemaService {

    @Autowired
    private TemaRepository temaRepository;

    @Autowired
    private NivelRepository nivelRepo;

    @Override
    public Optional<TemaResponse> get(String id) {
        return temaRepository.findById(id)
                .map(TemaResponse::convertirTema);
    }

    @Override
    public Map<Boolean, Optional<Tema>> create(Optional<TemaRequest> obj) {
        if (obj.isPresent() && !temaRepository.existsById(obj.get().id_tema())) {
            Tema tema = obj.get().toTema();

            // Asegurarse de que la colección solo contenga entidades gestionadas
            tema.getNiveles().clear();

            if (obj.get().niveles() != null) {
                obj.get().niveles()
                        .stream()
                        .forEach(nivelId -> nivelRepo.findById(nivelId)
                                .ifPresent(nivel -> {
                                    // usar la entidad gestionada: setear la relación y añadirla a tema
                                    nivel.setTema(tema);
                                    tema.getNiveles().add(nivel);
                                }));
            }

            // Guardar el tema (y las relaciones según el mapeo cascade)
            temaRepository.save(tema);
            return Map.of(true, Optional.of(tema));
        }

        return Map.of(false, Optional.empty());
    }

    @Override
    public boolean update(String id, Optional<TemaRequest> obj) {
        if (temaRepository.existsById(id) && obj.isPresent()) {
            Tema tema = obj.get().toTema();
            tema.setId_tema(id);

            // Corregir la condición: solo procesar si la lista no es null y no está vacía
            if (obj.get().niveles() != null && !obj.get().niveles().isEmpty()) {

                tema.getNiveles().clear();

                obj.get().niveles()
                        .stream()
                        .forEach(nivelId -> nivelRepo.findById(nivelId)
                                .ifPresent(tema.getNiveles()::add));
            }

            temaRepository.save(tema);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        if (temaRepository.existsById(id)) {
            temaRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
