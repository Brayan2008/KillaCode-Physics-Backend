package app.killacode.back_app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import app.killacode.back_app.dto.PuntuacionRequest;
import app.killacode.back_app.dto.PuntuacionResponse;
import app.killacode.back_app.model.Dias;
import app.killacode.back_app.model.Puntuacion_Semanal;
import app.killacode.back_app.model.Usuario;
import app.killacode.back_app.repository.PuntuacionRepository;
import app.killacode.back_app.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
class PuntuacionServiceImplTest {

    @Mock
    private PuntuacionRepository puntacionRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private PuntuacionServiceImpl puntuacionService;

    private Usuario user;

    @BeforeEach
    void setUp() {
        user = new Usuario(1L, "correo1@hotmail.com", "12345678", "Brayan", 31, LocalDate.of(2025, 8, 20), 12);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setScheme("http");
        request.setServerName("localhost");
        request.setServerPort(8080);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @AfterEach
    void tearDown() {
        RequestContextHolder.resetRequestAttributes();
    }

    @Test
    void findByUsuario_returnsMappedScoresWhenUserExists() {
        Puntuacion_Semanal p1 = new Puntuacion_Semanal(10L, Dias.LUNES, 100, user);
        Puntuacion_Semanal p2 = new Puntuacion_Semanal(11L, Dias.MARTES, 80, user);
        user.getPuntuaciones().addAll(List.of(p1, p2));

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<List<PuntuacionResponse>> result = puntuacionService.findByUsuario(1L);

        assertTrue(result.isPresent());
        assertEquals(2, result.get().size());
        assertEquals(10L, result.get().get(0).id());
        assertEquals(Dias.LUNES, result.get().get(0).dias());
        assertEquals(100, result.get().get(0).puntos());
        assertTrue(result.get().get(0).usuarioUrl().endsWith("/usuario/1"));
        assertEquals(11L, result.get().get(1).id());
    }

    @Test
    void findByUsuario_returnsEmptyWhenUserDoesNotExist() {
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<List<PuntuacionResponse>> result = puntuacionService.findByUsuario(99L);

        assertTrue(result.isEmpty());
    }

    @Test
    void update_returnsTrueWhenUserExistsRequestPresentAndOneRowUpdated() {
        PuntuacionRequest request = new PuntuacionRequest(Dias.JUEVES, 120);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(user));
        when(puntacionRepository.updatePuntos(eq(user), eq(Dias.JUEVES), eq(120))).thenReturn(1);

        boolean updated = puntuacionService.update(1L, Optional.of(request));

        assertTrue(updated);
    }

    @Test
    void update_returnsFalseWhenUserDoesNotExist() {
        PuntuacionRequest request = new PuntuacionRequest(Dias.JUEVES, 120);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        boolean updated = puntuacionService.update(1L, Optional.of(request));

        assertFalse(updated);
        verify(puntacionRepository, never()).updatePuntos(any(), any(), eq(120));
    }

    @Test
    void update_returnsFalseWhenRequestIsEmpty() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(user));

        boolean updated = puntuacionService.update(1L, Optional.empty());

        assertFalse(updated);
        verify(puntacionRepository, never()).updatePuntos(any(), any(), any(Integer.class));
    }

    @Test
    void update_returnsFalseWhenUpdatedRowsIsNotOne() {
        PuntuacionRequest request = new PuntuacionRequest(Dias.VIERNES, 50);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(user));
        when(puntacionRepository.updatePuntos(eq(user), eq(Dias.VIERNES), eq(50))).thenReturn(0);

        boolean updated = puntuacionService.update(1L, Optional.of(request));

        assertFalse(updated);
    }

    @Test
    void delete_returnsTrueWhenScoreExists() {
        when(puntacionRepository.existsById(10L)).thenReturn(true);

        boolean deleted = puntuacionService.delete(10L);

        assertTrue(deleted);
        verify(puntacionRepository).deleteById(10L);
    }

    @Test
    void delete_returnsFalseWhenScoreDoesNotExist() {
        when(puntacionRepository.existsById(10L)).thenReturn(false);

        boolean deleted = puntuacionService.delete(10L);

        assertFalse(deleted);
        verify(puntacionRepository, never()).deleteById(10L);
    }
}
