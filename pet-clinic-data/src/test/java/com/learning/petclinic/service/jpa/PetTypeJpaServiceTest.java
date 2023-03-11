package com.learning.petclinic.service.jpa;

import com.learning.petclinic.model.PetType;
import com.learning.petclinic.repository.PetTypeRepository;
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
class PetTypeJpaServiceTest {
    PetType dog;

    @Mock
    PetTypeRepository petTypeRepository;

    @InjectMocks
    PetTypeJpaService petTypeJpaService;

    @BeforeEach
    void setUp() {
        dog = PetType.builder()
                .id(1L)
                .name("Dog")
                .build();
    }

    @Test
    void findAll() {
        // given
        Set<PetType> petTypes = new HashSet<>();
        petTypes.add(dog);
        given(petTypeRepository.findAll()).willReturn(petTypes);
        // when
        Set<PetType> foundPets = petTypeJpaService.findAll();
        // then
        assertEquals(1, foundPets.size());
        verify(petTypeRepository, times(1)).findAll();
    }

    @DisplayName(value = "findById - existentId - returnsPetType")
    @Test
    void findById() {
        // given
        given(petTypeRepository.findById(1L)).willReturn(Optional.ofNullable(dog));
        // when
        PetType foundPetType = petTypeJpaService.findById(1L);
        // then
        assertEquals(1L, foundPetType.getId());
        verify(petTypeRepository, times(1)).findById(anyLong());
    }

    @DisplayName(value = "findById - nonExistentId - returnsNull")
    @Test
    void findById_nonExistentId() {
        // given
        given(petTypeRepository.findById(Long.MAX_VALUE)).willReturn(null);
        // when
        // then
        assertThrows(NullPointerException.class, () -> petTypeJpaService.findById(Long.MAX_VALUE));
        verify(petTypeRepository, times(1)).findById(anyLong());
    }

    @Test
    void delete() {
        // given
        // when
        petTypeJpaService.delete(dog);
        // then
        verify(petTypeRepository, times(1)).delete(any(PetType.class));
    }

    @Test
    void deleteById() {
        // given
        // when
        petTypeJpaService.deleteById(1L);
        // then
        verify(petTypeRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void save() {
        // given
        given(petTypeRepository.save(dog)).willReturn(dog);
        // when
        PetType savedPetType = petTypeJpaService.save(dog);
        // then
        assertNotNull(savedPetType);
        verify(petTypeRepository, times(1)).save(ArgumentMatchers.any(PetType.class));
    }
}