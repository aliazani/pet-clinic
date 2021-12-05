package com.learning.petclinic.service.jpa;

import com.learning.petclinic.model.Owner;
import com.learning.petclinic.model.Pet;
import com.learning.petclinic.model.PetType;
import com.learning.petclinic.repository.PetRepository;
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
class PetJpaServiceTest {
    Owner owner;
    Pet pet;
    PetType dog;

    @Mock
    PetRepository petRepository;

    @Mock
    PetTypeJpaService petTypeJpaService;

    @Mock
    OwnerJpaService ownerJpaService;

    @InjectMocks
    PetJpaService petJpaService;

    @BeforeEach
    void setUp() {
        dog = new PetType();
        dog.setName("Dog");
        petTypeJpaService.save(dog);

        owner = Owner.builder()
                .id(1L)
                .firstName("Richard")
                .lastName("Stallman")
                .city("New York")
                .address("1234 street.")
                .telephone("123456")
                .build();
        ownerJpaService.save(owner);

        pet = Pet.builder()
                .id(1L)
                .name("Gorge")
                .birthDate(LocalDate.now())
                .owner(owner)
                .petType(dog)
                .build();
    }

    @Test
    void findAll() {
        // given
        Set<Pet> pets = new HashSet<>();
        pets.add(pet);
        given(petRepository.findAll()).willReturn(pets);
        // when
        Set<Pet> foundPets = petJpaService.findAll();
        // then
        assertEquals(1, foundPets.size());
        verify(petRepository, times(1)).findAll();
    }

    @DisplayName(value = "findById - existentId - returnsPet")
    @Test
    void findById() {
        // given
        given(petRepository.findById(1L)).willReturn(Optional.ofNullable(pet));
        // when
        Pet foundPet = petJpaService.findById(1L);
        // then
        assertEquals(1L, foundPet.getId());
        verify(petRepository, times(1)).findById(anyLong());
    }

    @DisplayName(value = "findById - nonExistentId - returnsNull")
    @Test
    void findById_nonExistentId() {
        // given
        given(petRepository.findById(Long.MAX_VALUE)).willReturn(null);
        // when
        // then
        assertThrows(NullPointerException.class, () -> petJpaService.findById(Long.MAX_VALUE));
        verify(petRepository, times(1)).findById(anyLong());
    }

    @Test
    void delete() {
        // given
        // when
        petJpaService.delete(pet);
        // then
        verify(petRepository, times(1)).delete(any(Pet.class));
    }

    @Test
    void deleteById() {
        // given
        // when
        petJpaService.deleteById(1L);
        // then
        verify(petRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void save() {
        // given
        given(petRepository.save(pet)).willReturn(pet);
        // when
        Pet savedPet = petJpaService.save(pet);
        // then
        assertNotNull(savedPet);
        verify(petRepository, times(1)).save(ArgumentMatchers.any(Pet.class));
    }
}