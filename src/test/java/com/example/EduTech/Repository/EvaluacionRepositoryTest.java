package com.example.EduTech.Repository;

import com.example.EduTech.Model.Curso.Evaluacion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EvaluacionRepositoryTest {

    @Autowired
    private EvaluacionRepository evaluacionRepository;

    @Autowired
    private TestEntityManager entityManager;

    private Evaluacion evaluacionPrueba;

    @BeforeEach
    void setUp() {
        // Configuración inicial para cada test
        evaluacionPrueba = new Evaluacion();
        evaluacionPrueba.setMateria("Matemáticas");
        evaluacionPrueba.setNota(8.5);
        entityManager.persistAndFlush(evaluacionPrueba);
    }

    @Test
    void whenFindById_thenReturnEvaluacion() {
        // When
        Optional<Evaluacion> foundEvaluacion = evaluacionRepository.findById(evaluacionPrueba.getId());

        // Then
        assertTrue(foundEvaluacion.isPresent());
        assertEquals("Matemáticas", foundEvaluacion.get().getMateria());
        assertEquals(8.5, foundEvaluacion.get().getNota(), 0.001);
    }

    @Test
    void whenFindByNonExistingId_thenReturnEmpty() {
        // When
        Optional<Evaluacion> foundEvaluacion = evaluacionRepository.findById(-1);

        // Then
        assertFalse(foundEvaluacion.isPresent());
    }

    @Test
    void whenSaveEvaluacion_thenEvaluacionIsPersisted() {
        // Given
        Evaluacion nuevaEvaluacion = new Evaluacion();
        nuevaEvaluacion.setMateria("Historia");
        nuevaEvaluacion.setNota(7.0);

        // When
        Evaluacion savedEvaluacion = evaluacionRepository.save(nuevaEvaluacion);

        // Then
        assertNotNull(savedEvaluacion.getId());
        assertEquals("Historia", savedEvaluacion.getMateria());

        // Verify in DB
        Evaluacion dbEvaluacion = entityManager.find(Evaluacion.class, savedEvaluacion.getId());
        assertEquals(7.0, dbEvaluacion.getNota(), 0.001);
    }

    @Test
    void whenUpdateEvaluacion_thenEvaluacionIsUpdated() {
        // Given
        evaluacionPrueba.setMateria("Matemáticas Avanzadas");
        evaluacionPrueba.setNota(9.0);

        // When
        Evaluacion updatedEvaluacion = evaluacionRepository.save(evaluacionPrueba);

        // Then
        assertEquals(evaluacionPrueba.getId(), updatedEvaluacion.getId());
        assertEquals("Matemáticas Avanzadas", updatedEvaluacion.getMateria());
        assertEquals(9.0, updatedEvaluacion.getNota(), 0.001);

        // Verify in DB
        Evaluacion dbEvaluacion = entityManager.find(Evaluacion.class, evaluacionPrueba.getId());
        assertEquals("Matemáticas Avanzadas", dbEvaluacion.getMateria());
    }

    @Test
    void whenDeleteEvaluacion_thenEvaluacionIsRemoved() {
        // When
        evaluacionRepository.delete(evaluacionPrueba);

        // Then
        Evaluacion deletedEvaluacion = entityManager.find(Evaluacion.class, evaluacionPrueba.getId());
        assertNull(deletedEvaluacion);
    }

    @Test
    void whenFindAll_thenReturnAllEvaluaciones() {
        // Given
        Evaluacion evaluacion2 = new Evaluacion();
        evaluacion2.setMateria("Literatura");
        evaluacion2.setNota(6.5);
        entityManager.persistAndFlush(evaluacion2);

        // When
        List<Evaluacion> allEvaluaciones = evaluacionRepository.findAll();

        // Then
        assertEquals(2, allEvaluaciones.size());
    }

    @Test
    void whenFindByMateria_thenReturnEvaluacionesForMateria() {
        // Given
        Evaluacion evaluacion2 = new Evaluacion();
        evaluacion2.setMateria("Matemáticas");
        evaluacion2.setNota(7.8);
        entityManager.persistAndFlush(evaluacion2);

        Evaluacion evaluacion3 = new Evaluacion();
        evaluacion3.setMateria("Física");
        evaluacion3.setNota(8.0);
        entityManager.persistAndFlush(evaluacion3);

        // When
        List<Evaluacion> matematicasEvaluaciones = evaluacionRepository.findByMateria("Matemáticas");
        List<Evaluacion> fisicaEvaluaciones = evaluacionRepository.findByMateria("Física");

        // Then
        assertEquals(2, matematicasEvaluaciones.size());
        assertEquals(1, fisicaEvaluaciones.size());
        assertEquals("Física", fisicaEvaluaciones.get(0).getMateria());
    }
}