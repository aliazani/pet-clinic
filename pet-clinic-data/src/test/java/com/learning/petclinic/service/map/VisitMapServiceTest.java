package com.learning.petclinic.service.map;

import com.learning.petclinic.model.Visit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class VisitMapServiceTest {
    VisitMapService visitMapService;
    Visit visit;

    @BeforeEach
    void setUp() {
        visitMapService = new VisitMapService();

        visit = Visit.builder()
                .date(LocalDate.now())
                .description("Broken leg.")
                .build();

        visitMapService.save(visit);
    }

    @Test
    void findAll() {
        //given
        // when
        Set<Visit> visits = visitMapService.findAll();
        // then
        assertEquals(1, visits.size());
    }

    @DisplayName(value = "findById - existentId - returnsVisit")
    @Test
    void findById() {
        // given
        Long id = visit.getId();
        // when
        Visit foundVisit = visitMapService.findById(id);
        // then
        assertEquals(id, foundVisit.getId());
        assertEquals("Broken leg.", foundVisit.getDescription());
    }

    @DisplayName(value = "findById - nonExistentId - returnsNull")
    @Test
    void findById_nonExistentId() {
        // given
        Long id = Long.MAX_VALUE;
        // when
        Visit foundVisit = visitMapService.findById(id);
        // then
        assertNull(foundVisit);
    }

    @Test
    void delete() {
        // given
        // when
        visitMapService.delete(visit);
        // then
        assertEquals(0, visitMapService.findAll().size());
    }

    @Test
    void deleteById() {
        // given
        Long id = visit.getId();
        // when
        visitMapService.deleteById(id);
        // then
        assertEquals(0, visitMapService.findAll().size());
    }

    @Test
    void save() {
        // given
        Long id = 2L;
        Visit visit2 = new Visit();
        visit2.setDate(LocalDate.now());
        visit2.setDescription("Health check.");
        // when
        Visit savedVisit = visitMapService.save(visit2);
        // then
        assertEquals(id, savedVisit.getId());
    }


}