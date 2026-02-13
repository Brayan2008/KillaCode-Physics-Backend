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

import app.killacode.back_app.dto.SeccionRequest;
import app.killacode.back_app.model.Teoria;
import app.killacode.back_app.repository.SeccionRepository;
import app.killacode.back_app.repository.TeoriaRepository;

@ExtendWith(MockitoExtension.class)
class SeccionServiceImplTest {

    @Mock
    private SeccionRepository seccionRepository;

    @Mock
    private TeoriaRepository teoriaRepository;

    @InjectMocks
    private SeccionServiceImpl service;

    @Test
    void create_returnsTrueWhenSeccionAndTeoriaAreValid() {
        SeccionRequest request = new SeccionRequest("s1", "h", "b", "f", 1, "img", "te1");
        Teoria teoria = new Teoria();
        teoria.setId_teoria("te1");

        when(seccionRepository.existsById("s1")).thenReturn(false);
        when(teoriaRepository.existsById("te1")).thenReturn(true);
        when(teoriaRepository.findById("te1")).thenReturn(Optional.of(teoria));

        boolean created = service.create(Optional.of(request));

        assertTrue(created);
        verify(seccionRepository).save(any());
    }

    @Test
    void create_returnsFalseWhenTeoriaDoesNotExist() {
        SeccionRequest request = new SeccionRequest("s1", "h", "b", "f", 1, "img", "te1");

        when(seccionRepository.existsById("s1")).thenReturn(false);
        when(teoriaRepository.existsById("te1")).thenReturn(false);

        boolean created = service.create(Optional.of(request));

        assertFalse(created);
        verify(seccionRepository, never()).save(any());
    }

    @Test
    void update_returnsTrueWhenSeccionExistsAndTeoriaValid() {
        SeccionRequest request = new SeccionRequest("s1", "h", "b", "f", 1, "img", "te1");
        Teoria teoria = new Teoria();
        teoria.setId_teoria("te1");

        when(seccionRepository.existsById("s1")).thenReturn(true);
        when(teoriaRepository.existsById("te1")).thenReturn(true);
        when(teoriaRepository.findById("te1")).thenReturn(Optional.of(teoria));

        boolean updated = service.update("s1", Optional.of(request));

        assertTrue(updated);
        verify(seccionRepository).save(any());
    }

    @Test
    void delete_returnsTrueWhenSeccionExists() {
        when(seccionRepository.existsById("s1")).thenReturn(true);

        boolean deleted = service.delete("s1");

        assertTrue(deleted);
        verify(seccionRepository).deleteById("s1");
    }
}
