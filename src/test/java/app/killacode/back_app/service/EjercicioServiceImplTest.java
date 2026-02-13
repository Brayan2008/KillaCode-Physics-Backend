package app.killacode.back_app.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import app.killacode.back_app.dto.EjercicioDTO.EjercicioRequest;
import app.killacode.back_app.dto.EjercicioDTO.EjercicioUpdateRequest;
import app.killacode.back_app.model.Ejercicio;
import app.killacode.back_app.model.Practica;
import app.killacode.back_app.repository.EjercicioRepository;
import app.killacode.back_app.repository.PracticaRepository;

@ExtendWith(MockitoExtension.class)
class EjercicioServiceImplTest {

    @Mock
    private EjercicioRepository ejercicioRepository;

    @Mock
    private PracticaRepository practicaRepository;

    @InjectMocks
    private EjercicioServiceImpl service;

    @Test
    void create_returnsTrueWhenValidRequestAndPracticaExists() {
        EjercicioRequest request = new EjercicioRequest("e1", "p1", 10, 1, "texto", "resp", List.of("m1"), "retro", "img");
        Practica practica = new Practica();
        practica.setId_practica("p1");

        when(ejercicioRepository.existsById("e1")).thenReturn(false);
        when(practicaRepository.existsById("p1")).thenReturn(true);
        when(practicaRepository.findById("p1")).thenReturn(Optional.of(practica));

        var result = service.create(Optional.of(request));

        assertTrue(result.get(true).isPresent());
        verify(ejercicioRepository).save(any(Ejercicio.class));
    }

    @Test
    void create_returnsFalseWhenPracticaDoesNotExist() {
        EjercicioRequest request = new EjercicioRequest("e1", "p1", 10, 1, "texto", "resp", List.of(), "retro", "img");

        when(ejercicioRepository.existsById("e1")).thenReturn(false);
        when(practicaRepository.existsById("p1")).thenReturn(false);

        var result = service.create(Optional.of(request));

        assertTrue(result.get(false).isEmpty());
        verify(ejercicioRepository, never()).save(any(Ejercicio.class));
    }

    @Test
    void update_throwsWhenPracticaDoesNotExist() {
        EjercicioUpdateRequest request = new EjercicioUpdateRequest("p1", 10, 1, "texto", "resp", "retro", "img");

        when(ejercicioRepository.existsById("e1")).thenReturn(true);
        when(practicaRepository.findById("p1")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.update("e1", Optional.of(request)));
    }

    @Test
    void delete_returnsTrueWhenEjercicioExists() {
        when(ejercicioRepository.existsById("e1")).thenReturn(true);

        boolean deleted = service.delete("e1");

        assertTrue(deleted);
        verify(ejercicioRepository).deleteById("e1");
    }

    @Test
    void getEjercicios_returnsListWhenPracticaExists() {
        Practica practica = new Practica();
        practica.setId_practica("p1");
        practica.getEjercicios().add(new Ejercicio("e1", 10, 1, "texto", "resp", "retro", "img", practica));

        when(practicaRepository.existsById("p1")).thenReturn(true);
        when(practicaRepository.findById("p1")).thenReturn(Optional.of(practica));

        var result = service.getEjercicios("p1");

        assertTrue(result.isPresent());
        assertTrue(result.get().size() == 1);
    }

    @Test
    void getEjercicios_returnsEmptyWhenPracticaDoesNotExist() {
        when(practicaRepository.existsById("p1")).thenReturn(false);

        var result = service.getEjercicios("p1");

        assertTrue(result.isEmpty());
    }

    @Test
    void update_returnsFalseWhenEjercicioDoesNotExist() {
        EjercicioUpdateRequest request = new EjercicioUpdateRequest("p1", 10, 1, "texto", "resp", "retro", "img");
        when(ejercicioRepository.existsById("e1")).thenReturn(false);

        boolean updated = service.update("e1", Optional.of(request));

        assertFalse(updated);
    }
}
