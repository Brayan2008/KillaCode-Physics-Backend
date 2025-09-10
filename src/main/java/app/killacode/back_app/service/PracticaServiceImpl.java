package app.killacode.back_app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.killacode.back_app.model.Practica;
import app.killacode.back_app.repository.PracticaRepository;
import app.killacode.back_app.service.interfaces.PracticaService;

@Service
public class PracticaServiceImpl implements PracticaService {

    @Autowired
    private PracticaRepository practicaRepository;

    @Override
    public boolean crearPractica(Practica body) {
        if (body != null && !practicaRepository.existsById(body.getId_practica())) {
            practicaRepository.save(body);
            return true;
        }
        return false;
    }

}
