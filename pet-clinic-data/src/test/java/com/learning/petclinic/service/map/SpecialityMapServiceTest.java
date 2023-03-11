package com.learning.petclinic.service.map;

import com.learning.petclinic.model.Speciality;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class SpecialityMapServiceTest {
    SpecialityMapService specialityMapService;
    Speciality speciality;

    @BeforeEach
    void setUp() {
        specialityMapService = new SpecialityMapService();

        speciality = new Speciality();
        speciality.setName("Surgery");

        specialityMapService.save(speciality);
    }

    @Test
    void findAll() {
        //given
        // when
        Set<Speciality> specialities = specialityMapService.findAll();
        // then
        assertEquals(1, specialities.size());
    }

    @DisplayName(value = "findById - existentId - returnsPetType")
    @Test
    void findById() {
        // given
        Long id = speciality.getId();
        // when
        Speciality foundSpeciality = specialityMapService.findById(id);
        // then
        assertEquals(id, foundSpeciality.getId());
        assertEquals("Surgery", foundSpeciality.getName());
    }

    @DisplayName(value = "findById - nonExistentId - returnsNull")
    @Test
    void findById_nonExistentId() {
        // given
        Long id = Long.MAX_VALUE;
        // when
        Speciality foundSpeciality = specialityMapService.findById(id);
        // then
        assertNull(foundSpeciality);
    }

    @Test
    void delete() {
        // given
        // when
        specialityMapService.delete(speciality);
        // then
        assertEquals(0, specialityMapService.findAll().size());
    }

    @Test
    void deleteById() {
        // given
        Long id = speciality.getId();
        // when
        specialityMapService.deleteById(id);
        // then
        assertEquals(0, specialityMapService.findAll().size());
    }

    @Test
    void save() {
        // given
        Long id = 2L;
        Speciality speciality2 = new Speciality();
        speciality2.setName("Dentistry");
        // when
        Speciality savedSpeciality = specialityMapService.save(speciality2);
        // then
        assertEquals(id, savedSpeciality.getId());
    }
}