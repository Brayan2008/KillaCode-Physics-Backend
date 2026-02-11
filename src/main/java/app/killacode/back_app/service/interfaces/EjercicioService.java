package app.killacode.back_app.service.interfaces;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import app.killacode.back_app.dto.EjercicioDTO.*;
import app.killacode.back_app.dto.EjercicioResponse;
import app.killacode.back_app.model.Ejercicio;

public interface EjercicioService {

    Optional<EjercicioResponse> get(String id);

    Optional<List<EjercicioResponse>> getEjercicios(String idPractica);

    Map<Boolean, Optional<Ejercicio>> create(Optional<EjercicioRequest> obj);

    boolean update(String id, Optional<EjercicioUpdateRequest> obj) throws RuntimeException;

    boolean delete(String id);

}