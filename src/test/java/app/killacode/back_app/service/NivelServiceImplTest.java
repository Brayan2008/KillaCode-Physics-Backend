package app.killacode.back_app.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import app.killacode.back_app.dto.NivelRequest;
import app.killacode.back_app.model.Practica;
import app.killacode.back_app.model.Tema;
import app.killacode.back_app.model.Teoria;
import app.killacode.back_app.repository.NivelRepository;
import app.killacode.back_app.repository.PracticaRepository;
import app.killacode.back_app.repository.TemaRepository;
import app.killacode.back_app.repository.TeoriaRepository;

@ExtendWith(MockitoExtension.class)
class NivelServiceImplTest {

    @Mock
    private NivelRepository nivelRepo;

    @Mock
    private PracticaRepository practicaRepository;

    @Mock
    private TeoriaRepository teoriaRepo;

    @Mock
    private TemaRepository temaRepository;

    @InjectMocks
    private NivelServiceImpl service;

    @BeforeEach
    void setup() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setScheme("http");
        request.setServerName("localhost");
        request.setServerPort(8080);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @Test
    void create_returnsTrueWhenRequestIsValid() {
        NivelRequest request = new NivelRequest("n1", true, "p1", "te1", "t1");
        Practica p = new Practica();
        p.setId_practica("p1");
        Teoria te = new Teoria();
        te.setId_teoria("te1");
        Tema t = new Tema();
        t.setId_tema("t1");

        when(nivelRepo.existsById("n1")).thenReturn(false);
        when(practicaRepository.findById("p1")).thenReturn(Optional.of(p));
        when(teoriaRepo.findById("te1")).thenReturn(Optional.of(te));
        when(temaRepository.findById("t1")).thenReturn(Optional.of(t));
        when(nivelRepo.save(any())).thenAnswer(i -> i.getArgument(0));

        var result = service.create(Optional.of(request));

        assertTrue(result.get(true).isPresent());
        verify(nivelRepo).save(any());
    }

    @Test
    void create_returnsFalseWhenAlreadyExists() {
        NivelRequest request = new NivelRequest("n1", true, "p1", "te1", "t1");
        when(nivelRepo.existsById("n1")).thenReturn(true);

        var result = service.create(Optional.of(request));

        assertTrue(result.get(false).isEmpty());
        verify(nivelRepo, never()).save(any());
    }

    @Test
    void update_returnsTrueWhenNivelExists() {
        NivelRequest request = new NivelRequest("n1", false, null, null, null);
        when(nivelRepo.existsById("n1")).thenReturn(true);

        boolean updated = service.update("n1", Optional.of(request));

        assertTrue(updated);
        verify(nivelRepo).save(any());
    }

    @Test
    void update_returnsFalseWhenNivelDoesNotExist() {
        NivelRequest request = new NivelRequest("n1", false, null, null, null);
        when(nivelRepo.existsById("n1")).thenReturn(false);

        boolean updated = service.update("n1", Optional.of(request));

        assertFalse(updated);
        verify(nivelRepo, never()).save(any());
    }

    @Test
    void delete_returnsTrueWhenNivelExists() {
        when(nivelRepo.existsById("n1")).thenReturn(true);

        boolean deleted = service.delete("n1");

        assertTrue(deleted);
        verify(nivelRepo).deleteById("n1");
    }

    @Test
    void delete_returnsFalseWhenNivelDoesNotExist() {
        when(nivelRepo.existsById("n1")).thenReturn(false);

        boolean deleted = service.delete("n1");

        assertFalse(deleted);
        verify(nivelRepo, never()).deleteById("n1");
    }
}
