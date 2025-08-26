package app.killacode.back_app.service;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.killacode.back_app.dto.EjercicioRequest;
import app.killacode.back_app.dto.EjercicioResponse;
import app.killacode.back_app.model.Ejercicio;
import app.killacode.back_app.repository.EjercicioRepository;
import app.killacode.back_app.service.interfaces.EjercicioService;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class EjercicioServiceImpl implements EjercicioService {

    @Autowired
    private EjercicioRepository ejercicioRepository;

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
            if (id == null || !ejercicioRepository.existsById(id)) {
                ejercicioRepository.save(ejercicio);
                    return Map.of(true, Optional.of(ejercicio));
            }
        }
        return Map.of(false, Optional.empty()); //Map no acepta null
    }

    @Override
    public boolean update(String id, Optional<EjercicioRequest> obj) {
        if (obj.isPresent() && ejercicioRepository.existsById(id)) {
            var ejercicio = obj.get().toEjercicio();
            ejercicio.setId_ejercicio(id);
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


}

