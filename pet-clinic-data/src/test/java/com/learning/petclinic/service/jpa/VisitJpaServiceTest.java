package com.learning.petclinic.service.jpa;

import com.learning.petclinic.model.Owner;
import com.learning.petclinic.model.Pet;
import com.learning.petclinic.model.PetType;
import com.learning.petclinic.model.Visit;
import com.learning.petclinic.repository.VisitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
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
class VisitJpaServiceTest {
    Visit visit;
    Pet pet;
    PetType petType;
    Owner owner;

    @Mock
    PetJpaService petJpaService;

    @Mock
    VisitRepository visitRepository;

    @Mock
    PetTypeJpaService petTypeJpaService;

    @Mock
    OwnerJpaService ownerJpaService;

    @InjectMocks
    VisitJpaService visitJpaService;

    @BeforeEach
    void setUp() {
        petType = new PetType();
        petType.setName("Dog");
        petTypeJpaService.save(petType);

        owner = Owner.builder()
                .firstName("Richard")
                .lastName("Stallman")
                .city("New York")
                .address("1234 street.")
                .telephone("123456")
                .build();
        ownerJpaService.save(owner);

        pet = Pet.builder()
                .name("Gorge")
                .birthDate(LocalDate.now())
                .owner(owner)
                .petType(petType)
                .build();
        petJpaService.save(pet);

        visit = Visit.builder()
                .description("Health check.")
                .date(LocalDate.now())
                .pet(pet)
                .build();
    }

    @Test
    void findAll() {
        // given
        Set<Visit> visits = new HashSet<>();
        visits.add(visit);
        given(visitRepository.findAll()).willReturn(visits);
        // when
        Set<Visit> foundVisits = visitJpaService.findAll();
        // then
        assertEquals(1, foundVisits.size());
        verify(visitRepository, times(1)).findAll();
    }

    @DisplayName(value = "findById - existentId - returnsVisit")
    @Test
    void findById() {
        // given
        visit.setId(1L);
        given(visitRepository.findById(1L)).willReturn(Optional.ofNullable(visit));
        // when
        Visit foundVisit = visitJpaService.findById(1L);
        // then
        assertEquals(1L, foundVisit.getId());
        verify(visitRepository, times(1)).findById(anyLong());
    }


    @DisplayName(value = "findById - nonExistentId - returnsNull")
    @Test
    void findById_nonExistentId() {
        // given
        given(visitRepository.findById(Long.MAX_VALUE)).willReturn(null);
        // when
        // then
        assertThrows(NullPointerException.class, () -> visitJpaService.findById(Long.MAX_VALUE));
        verify(visitRepository, times(1)).findById(anyLong());
    }

    @Test
    void delete() {
        // given
        // when
        visitJpaService.delete(visit);
        // then
        verify(visitRepository, times(1)).delete(any(Visit.class));
    }

    @Test
    void deleteById() {
        // given
        // when
        visitJpaService.deleteById(1L);
        // then
        verify(visitRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void save() {
        // given
        given(visitRepository.save(visit)).willReturn(visit);
        // when
        Visit savedVisit = visitJpaService.save(visit);
        // then
        assertNotNull(savedVisit);
        verify(visitRepository, times(1)).save(ArgumentMatchers.any(Visit.class));
    }
}