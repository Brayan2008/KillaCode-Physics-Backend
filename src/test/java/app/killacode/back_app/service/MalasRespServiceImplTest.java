package app.killacode.back_app.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import app.killacode.back_app.dto.MalasRespuestasDTO;
import app.killacode.back_app.model.Ejercicio;
import app.killacode.back_app.model.MalasRespuestas;
import app.killacode.back_app.repository.EjercicioRepository;
import app.killacode.back_app.repository.MalasRespuestasRepository;

@ExtendWith(MockitoExtension.class)
class MalasRespServiceImplTest {

    @Mock
    private MalasRespuestasRepository malasRespRepository;

    @Mock
    private EjercicioRepository ejerciRepo;

    @InjectMocks
    private MalasRespServiceImpl service;

    @Test
    void create_returnsTrueWhenEjercicioExists() {
        MalasRespuestasDTO request = new MalasRespuestasDTO("mala", "e1");
        Ejercicio ejercicio = new Ejercicio();
        ejercicio.setId_ejercicio("e1");

        when(ejerciRepo.existsById("e1")).thenReturn(true);
        when(ejerciRepo.findById("e1")).thenReturn(Optional.of(ejercicio));

        var result = service.create(Optional.of(request));

        assertTrue(result.get(true).isPresent());
        verify(malasRespRepository).save(any(MalasRespuestas.class));
    }

    @Test
    void create_returnsFalseWhenEjercicioDoesNotExist() {
        MalasRespuestasDTO request = new MalasRespuestasDTO("mala", "e1");

        when(ejerciRepo.existsById("e1")).thenReturn(false);

        var result = service.create(Optional.of(request));

        assertTrue(result.get(false).isEmpty());
        verify(malasRespRepository, never()).save(any(MalasRespuestas.class));
    }

    @Test
    void update_returnsTrueWhenUpdatingOnlyContent() {
        long id = 1L;
        MalasRespuestasDTO request = new MalasRespuestasDTO("nuevo", null);
        Ejercicio ejercicio = new Ejercicio();
        ejercicio.setId_ejercicio("e1");
        MalasRespuestas existing = new MalasRespuestas(id, "old", ejercicio);

        when(malasRespRepository.existsById(id)).thenReturn(true);
        when(malasRespRepository.findById(id)).thenReturn(Optional.of(existing));

        boolean updated = service.update(id, Optional.of(request));

        assertTrue(updated);
        verify(malasRespRepository).save(any(MalasRespuestas.class));
    }

    @Test
    void delete_returnsTrueWhenExists() {
        when(malasRespRepository.existsById(1L)).thenReturn(true);

        boolean deleted = service.delete(1L);

        assertTrue(deleted);
        verify(malasRespRepository).deleteById(1L);
    }

    @Test
    void delete_returnsFalseWhenNotExists() {
        when(malasRespRepository.existsById(1L)).thenReturn(false);

        boolean deleted = service.delete(1L);

        assertFalse(deleted);
        verify(malasRespRepository, never()).deleteById(1L);
    }
}
