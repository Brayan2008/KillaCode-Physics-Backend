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

import app.killacode.back_app.model.Seccion;
import app.killacode.back_app.model.Teoria;
import app.killacode.back_app.repository.TeoriaRepository;

@ExtendWith(MockitoExtension.class)
class TeoriaServiceImplTest {

    @Mock
    private TeoriaRepository teoriaRepository;

    @InjectMocks
    private TeoriaServiceImpl service;

    @Test
    void getSecciones_returnsSectionsWhenTeoriaExists() {
        Teoria teoria = new Teoria();
        teoria.setId_teoria("te1");
        teoria.getSecciones().add(new Seccion("s1", "h", "b", "f", 1, "img", teoria));

        when(teoriaRepository.existsById("te1")).thenReturn(true);
        when(teoriaRepository.findById("te1")).thenReturn(Optional.of(teoria));

        var result = service.getSecciones("te1");

        assertTrue(result.isPresent());
        assertTrue(result.get().size() == 1);
    }

    @Test
    void getSecciones_returnsEmptyWhenTeoriaDoesNotExist() {
        when(teoriaRepository.existsById("te1")).thenReturn(false);

        var result = service.getSecciones("te1");

        assertTrue(result.isEmpty());
    }

    @Test
    void create_returnsTrueWhenValidAndNotExists() {
        Teoria teoria = new Teoria();
        teoria.setId_teoria("te1");

        when(teoriaRepository.existsById("te1")).thenReturn(false);

        boolean created = service.create(Optional.of(teoria));

        assertTrue(created);
        verify(teoriaRepository).save(teoria);
    }

    @Test
    void create_returnsFalseWhenAlreadyExists() {
        Teoria teoria = new Teoria();
        teoria.setId_teoria("te1");

        when(teoriaRepository.existsById("te1")).thenReturn(true);

        boolean created = service.create(Optional.of(teoria));

        assertFalse(created);
        verify(teoriaRepository, never()).save(any());
    }

    @Test
    void update_returnsTrueWhenTeoriaExists() {
        Teoria teoria = new Teoria();

        when(teoriaRepository.existsById("te1")).thenReturn(true);

        boolean updated = service.update("te1", Optional.of(teoria));

        assertTrue(updated);
        verify(teoriaRepository).save(teoria);
    }

    @Test
    void delete_returnsTrueWhenTeoriaExists() {
        when(teoriaRepository.existsById("te1")).thenReturn(true);

        boolean deleted = service.delete("te1");

        assertTrue(deleted);
        verify(teoriaRepository).deleteById("te1");
    }
}
