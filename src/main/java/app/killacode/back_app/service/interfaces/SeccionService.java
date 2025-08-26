package app.killacode.back_app.service.interfaces;

import java.util.Optional;

import app.killacode.back_app.dto.SeccionRequest;
import app.killacode.back_app.dto.SeccionResponse;

public interface SeccionService {

    Optional<SeccionResponse> get(String id);

    boolean create(Optional<SeccionRequest> obj);

    boolean delete(String id);

    boolean update(String id, Optional<SeccionRequest> obj);

}