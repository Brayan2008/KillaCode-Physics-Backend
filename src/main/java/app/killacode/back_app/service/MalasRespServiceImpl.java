package app.killacode.back_app.service;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.killacode.back_app.dto.MalasRespuestasDTO;
import app.killacode.back_app.dto.MalasRespuestasResponse;
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
    public Optional<MalasRespuestasResponse> get(long id) {
        return MalasRespRepository.findById(id).map(MalasRespuestasDTO::conversionMalas);
    }

    @Override
    public Map<Boolean, Optional<MalasRespuestas>> create(Optional<MalasRespuestasDTO> obj) {
        if (obj.isPresent()) {
            var mal_resp = guardarConEjercicio(obj);
            if (mal_resp != null) {
                MalasRespRepository.save(mal_resp);
                return Map.of(true, Optional.of(mal_resp));
            }
        }
        return Map.of(false, Optional.empty());
    }

    @Override
    public boolean update(long id, Optional<MalasRespuestasDTO> obj) {
        if (obj.isPresent() && MalasRespRepository.existsById(id)) {
            MalasRespuestas mal_resp = null;

            if (obj.get().id_ejercicio() != null) { 
                mal_resp = guardarConEjercicio(obj);
            } else { //Esto es por si en el request solo querian actualizar el contenido y no el ejercicio relacionado
                mal_resp = obj.get().toMalasRespuestas();
                mal_resp.setEjercicio(MalasRespRepository.findById(id).get().getEjercicio()); //OJO, en caso de encontrar un ejercicio relacionado con ese id lanzara excepcion
            } 
            
            if (mal_resp != null) {
                mal_resp.setId_mal(id);
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

    private MalasRespuestas guardarConEjercicio(Optional<MalasRespuestasDTO> obj) {
        var id_ejercicio = obj.get().id_ejercicio();
        if (id_ejercicio != null && ejerciRepo.existsById(id_ejercicio)) {
            var malasRespuestas = obj.get().toMalasRespuestas();
            malasRespuestas.setEjercicio(ejerciRepo.findById(id_ejercicio).get());
            return malasRespuestas;
        }
        return null;
    }
}
