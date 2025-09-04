package app.killacode.back_app.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.killacode.back_app.dto.UsuarioRequest;
import app.killacode.back_app.dto.UsuarioResponse;
import app.killacode.back_app.model.Dias;
import app.killacode.back_app.model.Puntuacion_Semanal;
import app.killacode.back_app.model.Usuario;
import app.killacode.back_app.repository.PuntacionRepository;
import app.killacode.back_app.repository.TemaRepository;
import app.killacode.back_app.repository.UsuarioRepository;
import app.killacode.back_app.service.interfaces.UsuarioService;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PuntacionRepository puntuacionRepo;

    @Autowired
    private TemaRepository temaRepo;
    
    @Override
    public Optional<UsuarioResponse> get(long id) {
        return usuarioRepository.findById(id).map(UsuarioResponse::fromUsuario);
    }

    @Override
    public Optional<UsuarioResponse> create(Optional<UsuarioRequest> req) {
        if (req.isPresent() && req.get().correo() != null && req.get().nombre() != null) {
            Usuario fetchuser = req.get().toUser();
            Dias[] dias = { Dias.DOMINGO, Dias.LUNES, Dias.MARTES, Dias.MIERCOLES, Dias.JUEVES, Dias.VIERNES,
                    Dias.SABADO };

            usuarioRepository.save(fetchuser);
           
            for (int i = 0; i <= 6; i++) {
                Puntuacion_Semanal p = new Puntuacion_Semanal(null, dias[i], 0, fetchuser);
                puntuacionRepo.save(p);
            }

            return Optional.of(UsuarioResponse.fromUsuario(fetchuser));
        }
        return Optional.empty();
    }

    @Override
    public boolean update(long id, Optional<UsuarioRequest> req) {
        if (req.isPresent() && usuarioRepository.existsById(id)) {
            Usuario u = usuarioRepository.findById(id).get();
            if (req.get().correo() != null)
                u.setCorreo(req.get().correo());
            if (req.get().nombre() != null)
                u.setNombre(req.get().nombre());
            // password puede ser null, pero si viene lo actualizamos
            if (req.get().password() != null)
                u.setPassword(req.get().password());
            usuarioRepository.save(u);
            return true;
        }
        return false;
    }

    @Override
    public boolean asignarTema(long id, String temaId) {
        if (usuarioRepository.existsById(id) && temaRepo.existsById(temaId)) {
            Usuario u = usuarioRepository.findById(id).get();
            u.getTemas().add(temaRepo.findById(temaId).get());
            usuarioRepository.save(u);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(long id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
