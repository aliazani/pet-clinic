package com.learning.petclinic.service.map;

import com.learning.petclinic.model.Pet;
import com.learning.petclinic.model.PetType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PetMapServiceTest {
    PetMapService petMapService;
    Pet pet;
    PetType dog;

    @BeforeEach
    void setUp() {
        petMapService = new PetMapService(new VisitMapService());

        dog = new PetType();
        dog.setName("dog");

        pet = Pet.builder()
                .name("doggy")
                .birthDate(LocalDate.now())
                .petType(dog)
                .build();

        petMapService.save(pet);
    }

    @Test
    void findAll() {
        //given
        // when
        Set<Pet> pets = petMapService.findAll();
        // then
        assertEquals(1, pets.size());
    }

    @DisplayName(value = "findById - existentId - returnsPet")
    @Test
    void findById() {
        // given
        Long id = pet.getId();
        // when
        Pet foundPet = petMapService.findById(id);
        // then
        assertEquals(id, foundPet.getId());
        assertEquals("doggy", foundPet.getName());
    }

    @DisplayName(value = "findById - nonExistentId - returnsNull")
    @Test
    void findById_nonExistentId() {
        // given
        Long id = Long.MAX_VALUE;
        // when
        Pet foundPet = petMapService.findById(id);
        // then
        assertNull(foundPet);
    }

    @Test
    void delete() {
        // given
        // when
        petMapService.delete(pet);
        // then
        assertEquals(0, petMapService.findAll().size());
    }

    @Test
    void deleteById() {
        // given
        Long id = pet.getId();
        // when
        petMapService.deleteById(id);
        // then
        assertEquals(0, petMapService.findAll().size());
    }

    @Test
    void save() {
        // given
        Long id = 2L;
        Pet pet2 = Pet.builder()
                .name("doggy")
                .birthDate(LocalDate.now())
                .petType(dog)
                .build();

        // when
        Pet savedPet = petMapService.save(pet2);
        // then
        assertEquals(id, savedPet.getId());
    }
}