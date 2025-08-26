package app.killacode.back_app.service.interfaces;

import java.util.Map;
import java.util.Optional;

import app.killacode.back_app.dto.MalasRespuestasDTO;
import app.killacode.back_app.dto.MalasRespuestasResponse;
import app.killacode.back_app.model.MalasRespuestas;

public interface MalasRespService {
    Optional<MalasRespuestasResponse> get(long id);

    Map<Boolean, Optional<MalasRespuestas>> create(Optional<MalasRespuestasDTO> obj);

    boolean update(long id, Optional<MalasRespuestasDTO> obj);

    boolean delete(long id);
}
