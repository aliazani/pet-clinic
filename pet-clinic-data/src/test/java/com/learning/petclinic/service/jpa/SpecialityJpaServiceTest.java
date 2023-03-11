package com.learning.petclinic.service.jpa;

import com.learning.petclinic.model.PetType;
import com.learning.petclinic.model.Speciality;
import com.learning.petclinic.repository.SpecialityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SpecialityJpaServiceTest {
    Speciality speciality;

    @Mock
    SpecialityRepository specialityRepository;

    @InjectMocks
    SpecialityJpaService specialityJpaService;

    @BeforeEach
    void setUp() {
        speciality = new Speciality();
        speciality.setName("Surgery");
    }

    @Test
    void findAll() {
        // given
        Set<Speciality> specialities = new HashSet<>();
        specialities.add(speciality);
        given(specialityRepository.findAll()).willReturn(specialities);
        // when
        Set<Speciality> foundSpecialties = specialityJpaService.findAll();
        // then
        assertEquals(1, foundSpecialties.size());
        verify(specialityRepository, times(1)).findAll();
    }

    @DisplayName(value = "findById - existentId - returnsSpeciality")
    @Test
    void findById() {
        // given
        speciality.setId(1L);
        given(specialityRepository.findById(1L)).willReturn(Optional.ofNullable(speciality));
        // when
        Speciality foundSpeciality = specialityJpaService.findById(1L);
        // then
        assertEquals(1L, foundSpeciality.getId());
        verify(specialityRepository, times(1)).findById(anyLong());
    }

    @DisplayName(value = "findById - nonExistentId - returnsNull")
    @Test
    void findById_nonExistentId() {
        // given
        given(specialityRepository.findById(Long.MAX_VALUE)).willReturn(null);
        // when
        // then
        assertThrows(NullPointerException.class, () -> specialityJpaService.findById(Long.MAX_VALUE));
        verify(specialityRepository, times(1)).findById(anyLong());
    }

    @Test
    void delete() {
        // given
        // when
        specialityJpaService.delete(speciality);
        // then
        verify(specialityRepository, times(1)).delete(any(Speciality.class));
    }

    @Test
    void deleteById() {
        // given
        // when
        specialityJpaService.deleteById(1L);
        // then
        verify(specialityRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void save() {
        // given
        given(specialityRepository.save(speciality)).willReturn(speciality);
        // when
        Speciality savedSpeciality = specialityJpaService.save(speciality);
        // then
        assertNotNull(savedSpeciality);
        verify(specialityRepository, times(1)).save(ArgumentMatchers.any(Speciality.class));
    }
}