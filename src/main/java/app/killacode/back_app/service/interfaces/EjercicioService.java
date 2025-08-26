package app.killacode.back_app.service.interfaces;

import java.util.Map;
import java.util.Optional;

import app.killacode.back_app.dto.EjercicioRequest;
import app.killacode.back_app.dto.EjercicioResponse;
import app.killacode.back_app.model.Ejercicio;

public interface EjercicioService {
    Optional<EjercicioResponse> get(String id);

    Map<Boolean, Optional<Ejercicio>> create(Optional<EjercicioRequest> obj);

    boolean update(String id, Optional<EjercicioRequest> obj);

    boolean delete(String id);

}