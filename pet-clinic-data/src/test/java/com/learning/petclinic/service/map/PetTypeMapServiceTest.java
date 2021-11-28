package com.learning.petclinic.service.map;

import com.learning.petclinic.model.PetType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PetTypeMapServiceTest {

    PetTypeMapService petTypeMapService;
    PetType petType;

    @BeforeEach
    void setUp() {
        petTypeMapService = new PetTypeMapService();

        petType = new PetType();
        petType.setName("Lion");

        petTypeMapService.save(petType);
    }

    @Test
    void findAll() {
        //given
        // when
        Set<PetType> petTypes = petTypeMapService.findAll();
        // then
        assertEquals(1, petTypes.size());
    }

    @DisplayName(value = "findById - existentId - returnsPetType")
    @Test
    void findById() {
        // given
        Long id = petType.getId();
        // when
        PetType foundPetType = petTypeMapService.findById(id);
        // then
        assertEquals(id, foundPetType.getId());
        assertEquals("Lion", foundPetType.getName());
    }

    @DisplayName(value = "findById - nonExistentId - returnsNull")
    @Test
    void findById_nonExistentId() {
        // given
        Long id = Long.MAX_VALUE;
        // when
        PetType foundPetType = petTypeMapService.findById(id);
        // then
        assertNull(foundPetType);
    }

    @Test
    void delete() {
        // given
        // when
        petTypeMapService.delete(petType);
        // then
        assertEquals(0, petTypeMapService.findAll().size());
    }

    @Test
    void deleteById() {
        // given
        Long id = petType.getId();
        // when
        petTypeMapService.deleteById(id);
        // then
        assertEquals(0, petTypeMapService.findAll().size());
    }

    @Test
    void save() {
        // given
        Long id = 2L;
        PetType petType2 = new PetType();
        petType2.setName("Bird");
        // when
        PetType savedPetType = petTypeMapService.save(petType2);
        // then
        assertEquals(id, savedPetType.getId());
    }
}