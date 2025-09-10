package app.killacode.back_app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.killacode.back_app.dto.PuntuacionRequest;
import app.killacode.back_app.dto.PuntuacionResponse;
import app.killacode.back_app.model.Usuario;
import app.killacode.back_app.repository.PuntacionRepository;
import app.killacode.back_app.repository.UsuarioRepository;
import app.killacode.back_app.service.interfaces.PuntuacionService;

@Service
public class PuntuacionServiceImpl implements PuntuacionService {

    @Autowired
    private PuntacionRepository puntacionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Optional<List<PuntuacionResponse>> findByUsuario(long idUsuario) {
        return usuarioRepository.findById(idUsuario)
                .map(u -> u.getPuntuaciones().stream().map(PuntuacionResponse::fromModel).toList());
    }

    @Override
    public boolean update(Long id_user, Optional<PuntuacionRequest> req) {

        Optional<Usuario> userById = usuarioRepository.findById(id_user);

        if (userById.isEmpty())
            return false;

        if (req.isPresent()) {
            PuntuacionRequest puntuacionRequest = req.get();
            int updatedRows = puntacionRepository.updatePuntos(userById.get(), puntuacionRequest.dia(),
                    puntuacionRequest.puntos());
            return updatedRows == 1; // Devuelve verdadero si se actualizó exactamente una fila
        }
        return false;
    }

    @Override
    public boolean delete(Long id) {
        if (puntacionRepository.existsById(id)) {
            puntacionRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
