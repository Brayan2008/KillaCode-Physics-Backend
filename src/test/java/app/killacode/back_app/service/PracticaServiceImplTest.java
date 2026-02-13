package app.killacode.back_app.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import app.killacode.back_app.model.Practica;
import app.killacode.back_app.repository.PracticaRepository;

@ExtendWith(MockitoExtension.class)
class PracticaServiceImplTest {

    @Mock
    private PracticaRepository practicaRepository;

    @InjectMocks
    private PracticaServiceImpl service;

    @Test
    void crearPractica_returnsTrueWhenBodyIsValidAndNotExists() {
        Practica practica = new Practica();
        practica.setId_practica("p1");

        when(practicaRepository.existsById("p1")).thenReturn(false);

        boolean created = service.crearPractica(practica);

        assertTrue(created);
        verify(practicaRepository).save(practica);
    }

    @Test
    void crearPractica_returnsFalseWhenBodyIsNull() {
        boolean created = service.crearPractica(null);

        assertFalse(created);
        verify(practicaRepository, never()).save(org.mockito.ArgumentMatchers.any());
    }

    @Test
    void crearPractica_returnsFalseWhenAlreadyExists() {
        Practica practica = new Practica();
        practica.setId_practica("p1");

        when(practicaRepository.existsById("p1")).thenReturn(true);

        boolean created = service.crearPractica(practica);

        assertFalse(created);
        verify(practicaRepository, never()).save(practica);
    }
}
