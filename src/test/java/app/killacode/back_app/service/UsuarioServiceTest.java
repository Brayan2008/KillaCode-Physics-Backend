package app.killacode.back_app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import app.killacode.back_app.dto.UsuarioDTORequest.UsuarioRequest;
import app.killacode.back_app.dto.UsuarioDTORequest.UsuarioUpdateRequest;
import app.killacode.back_app.model.Tema;
import app.killacode.back_app.model.Usuario;
import app.killacode.back_app.repository.PuntuacionRepository;
import app.killacode.back_app.repository.TemaRepository;
import app.killacode.back_app.repository.UsuarioRepository;
import app.killacode.back_app.service.UsuarioServiceImpl;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    UsuarioRepository usuarioRepository;

    @Mock
    TemaRepository temaRepository;

    @Mock
    PuntuacionRepository puntuacionRepo;

    @InjectMocks
    UsuarioServiceImpl usuarioService;

    @Test
    void deberiaAsignarTemaAlUsuario() {
        String id_tema = "tm01";
        long id_user = 12L;

        var user = new Usuario();
        user.setId_usuario(id_user);

        Tema tema = new Tema(id_tema,"Spring");

        when(usuarioRepository.findById(id_user)).thenReturn(Optional.of(user));
        when(temaRepository.findById(id_tema)).thenReturn(Optional.of(tema));

        var resultado = usuarioService.asignarTema(id_user, id_tema);

        assertTrue(resultado);
        assertEquals(tema, user.getTemas().getFirst());

        verify(usuarioRepository).findById(any());
        verify(temaRepository).findById(any());
    }

    @Test
    void testCreate() {
        UsuarioRequest request = new UsuarioRequest("test@mail.com", "123456", "Brayan", 20);

        MockHttpServletRequest httpRequest = new MockHttpServletRequest();
        httpRequest.setScheme("http");
        httpRequest.setServerName("localhost");
        httpRequest.setServerPort(8080);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(httpRequest));

        var result = usuarioService.create(Optional.of(request));

        assertTrue(result.isPresent());
        assertEquals("Brayan", result.get().nombre());
        assertEquals("test@mail.com", result.get().correo());

        verify(usuarioRepository).save(any(Usuario.class));
        verify(puntuacionRepo, times(7)).save(any());

    }

    @Test
    void testDelete() {
        long id = 10L;
        when(usuarioRepository.existsById(id)).thenReturn(true);

        var result = usuarioService.delete(id);

        assertTrue(result);
        verify(usuarioRepository).deleteById(id);

    }

    @Test
    void testGet() {
        long id = 12L;
        Usuario usuario = new Usuario();
        usuario.setId_usuario(id);
        usuario.setNombre("Brayan");
        usuario.setCorreo("test@mail.com");

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setScheme("http");
        request.setServerName("localhost");
        request.setServerPort(8080);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));

        var result = usuarioService.get(id);

        assertTrue(result.isPresent());
        assertEquals(id, result.get().id());
        assertEquals("Brayan", result.get().nombre());
        assertNotNull(result.get().puntuacionesUrl());

    }

    @Test
    void testUpdate() {
        long id = 12L;
        Usuario usuario = new Usuario();
        usuario.setId_usuario(id);
        usuario.setNombre("Antiguo");
        usuario.setCorreo("old@mail.com");
        usuario.setPassword("oldPass");

        UsuarioUpdateRequest updateRequest = new UsuarioUpdateRequest("new@mail.com", "newPass", "Nuevo");

        when(usuarioRepository.existsById(id)).thenReturn(true);
        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));

        var result = usuarioService.update(id, Optional.of(updateRequest));

        assertTrue(result);
        assertEquals("new@mail.com", usuario.getCorreo());
        assertEquals("newPass", usuario.getPassword());
        assertEquals("Nuevo", usuario.getNombre());
        verify(usuarioRepository).save(usuario);

        assertFalse(usuarioService.update(999L, Optional.of(updateRequest)));

    }
}
