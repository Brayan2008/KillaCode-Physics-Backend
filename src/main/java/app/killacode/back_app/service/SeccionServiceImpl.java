package app.killacode.back_app.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.killacode.back_app.dto.SeccionRequest;
import app.killacode.back_app.dto.SeccionResponse;
import app.killacode.back_app.model.Seccion;
import app.killacode.back_app.repository.SeccionRepository;
import app.killacode.back_app.repository.TeoriaRepository;
import app.killacode.back_app.service.interfaces.SeccionService;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class SeccionServiceImpl implements SeccionService {

    @Autowired
    private SeccionRepository seccionRepository;

    @Autowired
    private TeoriaRepository teoriaRepository;

    @Override
    public Optional<SeccionResponse> get(String id) {
        return seccionRepository.findById(id).map(SeccionResponse::fromSeccion);
    }

    @Override
    public boolean create(Optional<SeccionRequest> obj) {
        if (obj.isPresent() && !seccionRepository.existsById(obj.get().id())) {
            var seccion = guardarConTeoria(obj);
            if (seccion != null) {
                seccionRepository.save(seccion);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean update(String id, Optional<SeccionRequest> obj) {
        if (obj.isPresent() && seccionRepository.existsById(id)) {
            var seccion = guardarConTeoria(obj);
            seccion.setId_section(id);
            if (seccion != null) {
                seccionRepository.save(seccion);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        if (seccionRepository.existsById(id)) {
            seccionRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private Seccion guardarConTeoria(Optional<SeccionRequest> obj) {
        var id_teoria = obj.get().id_teoria();
        if (id_teoria != null && teoriaRepository.existsById(id_teoria)) {
            var seccion = obj.get().toSeccion();
            seccion.setTeoria(teoriaRepository.findById(id_teoria).get());
            return seccion;
        }
        return null;
    }

}
