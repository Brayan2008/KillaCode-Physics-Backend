package app.killacode.back_app.service.interfaces;

import java.util.List;
import java.util.Optional;

import app.killacode.back_app.dto.SeccionResponse;
import app.killacode.back_app.dto.TeoriaResponse;
import app.killacode.back_app.model.Teoria;

public interface TeoriaService {

    Optional<TeoriaResponse> get(String id);

    boolean create(Optional<Teoria> obj);

    boolean delete(String id);

    boolean update(String id, Optional<Teoria> obj);

    Optional<List<SeccionResponse>> getSecciones(String id);

}