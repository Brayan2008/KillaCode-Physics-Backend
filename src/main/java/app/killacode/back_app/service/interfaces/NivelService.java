package app.killacode.back_app.service.interfaces;

import java.util.Map;
import java.util.Optional;

import app.killacode.back_app.dto.NivelRequest;
import app.killacode.back_app.dto.NivelResponse;

public interface NivelService {
    Optional<NivelResponse> get(String id);
    Map<Boolean, Optional<NivelResponse>> create(Optional<NivelRequest> obj);
    boolean update(String id, Optional<NivelRequest> obj);
    boolean delete(String id);
}
