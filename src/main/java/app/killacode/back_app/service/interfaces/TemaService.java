package app.killacode.back_app.service.interfaces;

import java.util.Map;
import java.util.Optional;

import app.killacode.back_app.dto.TemaRequest;
import app.killacode.back_app.dto.TemaResponse;
import app.killacode.back_app.model.Tema;

public interface TemaService {

    Optional<TemaResponse> get(String id);

    Map<Boolean, Optional<Tema>> create(Optional<TemaRequest> obj);

    boolean update(String id, Optional<TemaRequest> obj);

    boolean delete(String id);

}
