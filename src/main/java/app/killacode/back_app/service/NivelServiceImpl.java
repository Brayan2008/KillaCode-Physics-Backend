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
public class NivelServiceImpl implements NivelService{

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
        if (obj.isPresent() && nivelRepo.existsById(obj.get().id_nivel())) {
            
            var nivel = obj.get().convertirToNivel();
            ejercicioRepository.findById(obj.get().ejercicio()).ifPresent(nivel::setEjercicio);
            teoriaRepo.findById(obj.get().teoria()).ifPresent(nivel::setTeoria);
            temaRepository.findById(obj.get().tema()).ifPresent(nivel::setTema);

            return Map.of(true, Optional.of(NivelResponse.convertirNivel(nivelRepo.save(nivel))));
        }
        return Map.of(false, Optional.empty());
    }

    @Override
    public boolean update(String id, Optional<NivelRequest> obj) {
        if (obj.isPresent() && nivelRepo.existsById(id)) {
            var nivel = obj.get().convertirToNivel();
            nivel.setId_nivel(id);
            ejercicioRepository.findById(obj.get().ejercicio()).ifPresent(nivel::setEjercicio);
            teoriaRepo.findById(obj.get().teoria()).ifPresent(nivel::setTeoria);
            temaRepository.findById(obj.get().tema()).ifPresent(nivel::setTema);
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

}
