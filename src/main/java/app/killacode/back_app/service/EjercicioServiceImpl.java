package app.killacode.back_app.service;

import java.util.Optional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.killacode.back_app.dto.EjercicioResponse;
import app.killacode.back_app.model.Ejercicio;
import app.killacode.back_app.model.MalasRespuestas;
import app.killacode.back_app.repository.EjercicioRepository;
import app.killacode.back_app.service.interfaces.EjercicioService;

@Service
public class EjercicioServiceImpl implements EjercicioService {

    @Autowired
    private EjercicioRepository ejercicioRepository;

    @Override
    public Optional<EjercicioResponse> get(String id) {
        return ejercicioRepository.findById(id).map(EjercicioResponse::conversionEjercicio);
    }

    @Override
    public Optional<List<MalasRespuestas>> getMalas(String id) {
        return ejercicioRepository.findById(id).map(Ejercicio::getLista_malas);
    }

    @Override
    public boolean create(Optional<Ejercicio> obj) {
        if (obj.isPresent()) {
            var ejercicio = obj.get();
            var id = ejercicio.getId_ejercicio();
            // permitir crear si no existe id o si el id no existe en repo
            if (id == null || !ejercicioRepository.existsById(id)) {
                ejercicioRepository.save(ejercicio);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean update(String id, Optional<Ejercicio> obj) {
        if (obj.isPresent() && ejercicioRepository.existsById(id)) {
            var ejercicio = obj.get();
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

