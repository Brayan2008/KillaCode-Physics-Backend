package app.killacode.back_app.service.interfaces;

import java.util.Optional;

import app.killacode.back_app.dto.MalasRespuestasRequest;
import app.killacode.back_app.model.MalasRespuestas;

public interface MalasRespService {
    Optional<MalasRespuestas> get(long id);

    boolean create(Optional<MalasRespuestasRequest> obj);

    boolean update(long id, Optional<MalasRespuestasRequest> obj);

    boolean delete(long id);
}
