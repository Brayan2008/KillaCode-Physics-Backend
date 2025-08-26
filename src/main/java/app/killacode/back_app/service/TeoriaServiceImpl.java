package app.killacode.back_app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.killacode.back_app.dto.SeccionResponse;
import app.killacode.back_app.dto.TeoriaResponse;
import app.killacode.back_app.model.Teoria;
import app.killacode.back_app.repository.TeoriaRepository;
import app.killacode.back_app.service.interfaces.TeoriaService;

@Service
public class TeoriaServiceImpl implements TeoriaService {

    @Autowired
    private TeoriaRepository teoriaRepository;

    @Override
    public Optional<TeoriaResponse> get(String id) {
        return teoriaRepository.findById(id).map(TeoriaResponse::conversionTeoria);
    }

    @Override
    public Optional<List<SeccionResponse>> getSecciones(String id) {
        return teoriaRepository.existsById(id)
                ? Optional.of(teoriaRepository.findById(id).get().getSecciones().stream().map(SeccionResponse::fromSeccion).toList())
                : Optional.empty();
    }

    @Override
    public boolean create(Optional<Teoria> obj) {
        if (obj.isPresent() && obj.get().getId_teoria() != null
                && !teoriaRepository.existsById(obj.get().getId_teoria())) {
            teoriaRepository.save(obj.get());
            return true;
        }
        return false;
    }

    @Override
    public boolean update(String id, Optional<Teoria> obj) {
        if (obj.isPresent() && id != null && teoriaRepository.existsById(id)) {
            Teoria teoria = obj.get();
            teoria.setId_teoria(id);
            teoriaRepository.save(teoria);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        if (id != null && !id.isEmpty() && teoriaRepository.existsById(id)) {
            teoriaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}