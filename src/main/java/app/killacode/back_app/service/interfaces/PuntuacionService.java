package app.killacode.back_app.service.interfaces;

import java.util.List;
import java.util.Optional;

import app.killacode.back_app.dto.PuntuacionResponse;
import app.killacode.back_app.dto.PuntuacionRequest;

public interface PuntuacionService {

    Optional<List<PuntuacionResponse>> findByUsuario(long idUsuario);

    boolean update(Long id, Optional<PuntuacionRequest> req);

    boolean delete(Long id);

}
