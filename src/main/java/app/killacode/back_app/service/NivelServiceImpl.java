package app.killacode.back_app.service;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.killacode.back_app.dto.NivelRequest;
import app.killacode.back_app.dto.NivelResponse;
import app.killacode.back_app.repository.EjercicioRepository;
import app.killacode.back_app.repository.NivelRepository;
import app.killacode.back_app.repository.TemaRepository;
import app.killacode.back_app.repository.TeoriaRepository;
import app.killacode.back_app.service.interfaces.NivelService;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class NivelServiceImpl implements NivelService {

    @Autowired
    private NivelRepository nivelRepo;

    @Autowired
    private EjercicioRepository ejercicioRepository;

    @Autowired
    private TeoriaRepository teoriaRepo;

    @Autowired
    private TemaRepository temaRepository;

    @Override
    public Optional<NivelResponse> get(String id) {
        return nivelRepo.findById(id).map(NivelResponse::convertirNivel);
    }

    @Override
    public Map<Boolean, Optional<NivelResponse>> create(Optional<NivelRequest> obj) {
        if (obj.isPresent() && !nivelRepo.existsById(obj.get().id_nivel())) {
            var nivelObtenido = obj.get();
            var nivel = nivelObtenido.convertirToNivel();

            setRelatedEntities(nivelObtenido, nivel);

            return Map.of(true, Optional.of(NivelResponse.convertirNivel(nivelRepo.save(nivel))));
        }
        return Map.of(false, Optional.empty());
    }

    @Override
    public boolean update(String id, Optional<NivelRequest> obj) {
        if (obj.isPresent() && nivelRepo.existsById(id)) {
            var nivelObtenido = obj.get();
            var nivel = nivelObtenido.convertirToNivel();
            nivel.setId_nivel(id);

            setRelatedEntities(nivelObtenido, nivel);

            nivelRepo.save(nivel);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        if (nivelRepo.existsById(id)) {
            nivelRepo.deleteById(id);
            return true;
        }
        return false;
    }

     // Método privado para simplificar la asignación de entidades relacionadas
    private void setRelatedEntities(NivelRequest nivelObtenido, app.killacode.back_app.model.Nivel nivel) {
        if (nivelObtenido.ejercicio() != null) {
            ejercicioRepository.findById(nivelObtenido.ejercicio()).ifPresent(nivel::setEjercicio);
        }
        if (nivelObtenido.teoria() != null) {
            teoriaRepo.findById(nivelObtenido.teoria()).ifPresent(nivel::setTeoria);
        }
        if (nivelObtenido.tema() != null) {
            temaRepository.findById(nivelObtenido.tema()).ifPresent(nivel::setTema);
        }
    }
}
