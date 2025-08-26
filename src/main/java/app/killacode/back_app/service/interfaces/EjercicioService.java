package app.killacode.back_app.service.interfaces;

import java.util.Optional;
import java.util.List;

import app.killacode.back_app.dto.EjercicioResponse;
import app.killacode.back_app.model.Ejercicio;
import app.killacode.back_app.model.MalasRespuestas;

public interface EjercicioService {
    Optional<EjercicioResponse> get(String id);

    Optional<List<MalasRespuestas>> getMalas(String id);

    boolean create(Optional<Ejercicio> obj);

    boolean update(String id, Optional<Ejercicio> obj);

    boolean delete(String id);

}