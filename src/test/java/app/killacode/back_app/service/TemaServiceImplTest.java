package app.killacode.back_app.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
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

import app.killacode.back_app.dto.TemaRequest;
import app.killacode.back_app.model.Nivel;
import app.killacode.back_app.model.Tema;
import app.killacode.back_app.repository.NivelRepository;
import app.killacode.back_app.repository.TemaRepository;

@ExtendWith(MockitoExtension.class)
class TemaServiceImplTest {

    @Mock
    private TemaRepository temaRepository;

    @Mock
    private NivelRepository nivelRepo;

    @InjectMocks
    private TemaServiceImpl service;

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
        TemaRequest request = new TemaRequest("t1", "Tema 1", List.of("n1"));
        Nivel nivel = new Nivel();
        nivel.setId_nivel("n1");

        when(temaRepository.existsById("t1")).thenReturn(false);
        when(nivelRepo.findById("n1")).thenReturn(Optional.of(nivel));

        var result = service.create(Optional.of(request));

        assertTrue(result.get(true).isPresent());
        verify(temaRepository).save(any(Tema.class));
    }

    @Test
    void create_returnsFalseWhenAlreadyExists() {
        TemaRequest request = new TemaRequest("t1", "Tema 1", List.of("n1"));
        when(temaRepository.existsById("t1")).thenReturn(true);

        var result = service.create(Optional.of(request));

        assertTrue(result.get(false).isEmpty());
        verify(temaRepository, never()).save(any(Tema.class));
    }

    @Test
    void update_returnsTrueWhenTemaExists() {
        TemaRequest request = new TemaRequest("t1", "Tema nuevo", List.of("n1"));
        Nivel nivel = new Nivel();
        nivel.setId_nivel("n1");

        when(temaRepository.existsById("t1")).thenReturn(true);
        when(nivelRepo.findById("n1")).thenReturn(Optional.of(nivel));

        boolean updated = service.update("t1", Optional.of(request));

        assertTrue(updated);
        verify(temaRepository).save(any(Tema.class));
    }

    @Test
    void update_returnsFalseWhenTemaDoesNotExist() {
        TemaRequest request = new TemaRequest("t1", "Tema nuevo", List.of("n1"));
        when(temaRepository.existsById("t1")).thenReturn(false);

        boolean updated = service.update("t1", Optional.of(request));

        assertFalse(updated);
        verify(temaRepository, never()).save(any(Tema.class));
    }

    @Test
    void delete_returnsTrueWhenTemaExists() {
        when(temaRepository.existsById("t1")).thenReturn(true);

        boolean deleted = service.delete("t1");

        assertTrue(deleted);
        verify(temaRepository).deleteById("t1");
    }

    @Test
    void delete_returnsFalseWhenTemaDoesNotExist() {
        when(temaRepository.existsById("t1")).thenReturn(false);

        boolean deleted = service.delete("t1");

        assertFalse(deleted);
        verify(temaRepository, never()).deleteById("t1");
    }
}
