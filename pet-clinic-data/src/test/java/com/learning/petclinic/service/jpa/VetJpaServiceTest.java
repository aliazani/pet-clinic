package com.learning.petclinic.service.jpa;

import com.learning.petclinic.model.Speciality;
import com.learning.petclinic.model.Vet;
import com.learning.petclinic.repository.VetRepository;
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
class VetJpaServiceTest {
    Vet vet;

    @Mock
    SpecialityJpaService specialityJpaService;

    @Mock
    VetRepository vetRepository;

    @InjectMocks
    VetJpaService vetJpaService;

    @BeforeEach
    void setUp() {
        Speciality speciality = Speciality.builder()
                .id(1L)
                .name("Surgery")
                .build();
        specialityJpaService.save(speciality);

        vet = Vet.builder()
                .id(1L)
                .firstName("Majid")
                .lastName("Samie")
                .specialities(specialityJpaService.findAll())
                .build();
    }

    @Test
    void findAll() {
        // given
        Set<Vet> vets = new HashSet<>();
        vets.add(vet);
        given(vetRepository.findAll()).willReturn(vets);
        // when
        Set<Vet> foundVets = vetJpaService.findAll();
        // then
        assertEquals(1, foundVets.size());
        verify(vetRepository, times(1)).findAll();
    }

    @DisplayName(value = "findById - existentId - returnsVet")
    @Test
    void findById() {
        // given
        given(vetRepository.findById(1L)).willReturn(Optional.ofNullable(vet));
        // when
        Vet foundVet = vetJpaService.findById(1L);
        // then
        assertEquals(1L, foundVet.getId());
        verify(vetRepository, times(1)).findById(anyLong());
    }

    @DisplayName(value = "findById - nonExistentId - returnsNull")
    @Test
    void findById_nonExistentId() {
        // given
        given(vetRepository.findById(Long.MAX_VALUE)).willReturn(null);
        // when
        // then
        assertThrows(NullPointerException.class, () -> vetJpaService.findById(Long.MAX_VALUE));
        verify(vetRepository, times(1)).findById(anyLong());
    }

    @Test
    void delete() {
        // given
        // when
        vetJpaService.delete(vet);
        // then
        verify(vetRepository, times(1)).delete(any(Vet.class));
    }

    @Test
    void deleteById() {
        // given
        // when
        vetJpaService.deleteById(1L);
        // then
        verify(vetRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void save() {
        // given
        given(vetRepository.save(vet)).willReturn(vet);
        // when
        Vet savedVet = vetJpaService.save(vet);
        // then
        assertNotNull(savedVet);
        verify(vetRepository, times(1)).save(ArgumentMatchers.any(Vet.class));
    }
}