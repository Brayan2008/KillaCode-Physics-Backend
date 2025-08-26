package app.killacode.back_app.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.killacode.back_app.dto.MalasRespuestasRequest;
import app.killacode.back_app.model.MalasRespuestas;
import app.killacode.back_app.repository.EjercicioRepository;
import app.killacode.back_app.repository.MalasRespuestasRepository;
import app.killacode.back_app.service.interfaces.MalasRespService;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class MalasRespServiceImpl implements MalasRespService {

    @Autowired
    private MalasRespuestasRepository MalasRespRepository;
 
    @Autowired
    private EjercicioRepository ejerciRepo;

    @Override
    public Optional<MalasRespuestas> get(long id) {
        return MalasRespRepository.findById(id);
    }

    @Override
    public boolean create(Optional<MalasRespuestasRequest> obj) {
        if (obj.isPresent()) {
            var mal_resp = guardarConEjercicio(obj);
            if (mal_resp != null) {
                MalasRespRepository.save(mal_resp);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean update(long id, Optional<MalasRespuestasRequest> obj) {
        if (obj.isPresent() && MalasRespRepository.existsById(id)) {
            var mal_resp = guardarConEjercicio(obj);
            if (mal_resp != null) {
                MalasRespRepository.save(mal_resp);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean delete(long id) {
        if (MalasRespRepository.existsById(id)) {
            MalasRespRepository.deleteById(id);
            return true;
        }
        return false;
    }
    private MalasRespuestas guardarConEjercicio(Optional<MalasRespuestasRequest> obj) {
        var id_ejericio = obj.get().id_ejericio();
        if (id_ejericio != null && ejerciRepo.existsById(id_ejericio)) {
            var malasRespuestas = obj.get().toMalasRespuestas();
            malasRespuestas.setEjercicio(ejerciRepo.findById(id_ejericio).get());
            return malasRespuestas;
        }
        return null;
    }
}
