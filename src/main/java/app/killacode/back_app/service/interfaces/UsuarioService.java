package app.killacode.back_app.service.interfaces;

import java.util.Optional;

import app.killacode.back_app.dto.UsuarioRequest;
import app.killacode.back_app.dto.UsuarioResponse;

public interface UsuarioService {

	Optional<UsuarioResponse> get(long id);

	Optional<UsuarioResponse> create(Optional<UsuarioRequest> obj);

	boolean update(long id, Optional<UsuarioRequest> obj);

	boolean asignarTema(long id, String temaId);

	boolean delete(long id);

}
