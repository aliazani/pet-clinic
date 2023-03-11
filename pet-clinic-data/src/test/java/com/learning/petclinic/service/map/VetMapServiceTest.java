package com.learning.petclinic.service.map;

import com.learning.petclinic.model.Vet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class VetMapServiceTest {
    VetMapService vetMapService;
    Vet vet;

    @BeforeEach
    void setUp() {
        vetMapService = new VetMapService(new SpecialityMapService());

        vet = Vet.builder().
                firstName("Majid")
                .lastName("Samii")
                .build();

        vetMapService.save(vet);
    }

    @Test
    void findAll() {
        // given
        // when
        Set<Vet> vets = vetMapService.findAll();
        System.out.println(vets);
        //then
        assertEquals(1, vets.size());
    }

    @DisplayName(value = "findById - existentId - returnsVet")
    @Test
    void findById() {
        // given
        Long id = vet.getId();
        // when
        Vet foundVet = vetMapService.findById(id);
        // then
        assertEquals(id, foundVet.getId());
        assertEquals("Majid", foundVet.getFirstName());
        assertEquals("Samii", foundVet.getLastName());
    }

    @DisplayName(value = "findById - nonExistentId - returnsNull")
    @Test
    void findById_nonExistentId() {
        // given
        Long id = Long.MAX_VALUE;
        // when
        Vet foundVet = vetMapService.findById(id);
        // then
        assertNull(foundVet);
    }

    @Test
    void delete() {
        // given
        // when
        vetMapService.delete(vet);
        // then
        assertEquals(0, vetMapService.findAll().size());
    }

    @Test
    void deleteById() {
        // given
        Long id = vet.getId();
        // when
        vetMapService.deleteById(id);
        //then
        assertEquals(0, vetMapService.findAll().size());
    }

    @Test
    void save() {
        // given
        Long id = 2L;
        Vet vet2 = Vet.builder()
                .firstName("David")
                .lastName("Jobs")
                .build();
        // when
        Vet savedVet = vetMapService.save(vet2);
        // then
        assertEquals(id, savedVet.getId());
    }
}