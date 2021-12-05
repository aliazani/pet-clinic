package com.learning.petclinic.service.jpa;

import com.learning.petclinic.model.Owner;
import com.learning.petclinic.repository.OwnerRepository;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OwnerJpaServiceTest {
    Owner owner1;
    Owner owner2;
    Owner owner3;

    @Mock
    OwnerRepository ownerRepository;

    @InjectMocks
    OwnerJpaService ownerJpaService;

    @BeforeEach
    void setUp() {
        owner1 = Owner.builder()
                .id(1L)
                .firstName("Richard")
                .lastName("Stallman")
                .city("Michigan")
                .address("43 street.")
                .telephone("123456")
                .build();

        owner2 = Owner.builder()
                .id(2L)
                .firstName("Carlos")
                .lastName("Stallman")
                .city("NewYork")
                .address("apple street.")
                .telephone("1123123124")
                .build();

        owner3 = Owner.builder()
                .id(3L)
                .firstName("Robert")
                .lastName("Martin")
                .city("Bayern")
                .address("492 street.")
                .telephone("3892973423")
                .build();
    }

    @Test
    void findAll() {
        // given
        Set<Owner> owners = new HashSet<>();
        owners.add(owner1);
        given(ownerRepository.findAll()).willReturn(owners);
        // when
        Set<Owner> foundOwners = ownerJpaService.findAll();
        // then
        assertEquals(1, foundOwners.size());
        verify(ownerRepository, times(1)).findAll();
    }

    @DisplayName(value = "findById - existentId - returnsOwner")
    @Test
    void findById() {
        // given
        given(ownerRepository.findById(1L)).willReturn(Optional.ofNullable(owner1));
        // when
        Owner foundOwner = ownerJpaService.findById(1L);
        // then
        assertEquals(1L, foundOwner.getId());
        verify(ownerRepository, times(1)).findById(anyLong());
    }

    @DisplayName(value = "findById - nonExistentId - returnsNull")
    @Test
    void findById_nonExistentId() {
        // given
        given(ownerRepository.findById(Long.MAX_VALUE)).willReturn(null);
        // when
        // then
        assertThrows(NullPointerException.class, () -> ownerJpaService.findById(Long.MAX_VALUE));
        verify(ownerRepository, times(1)).findById(anyLong());
    }

    @DisplayName(value = "findByLastName - existentLastName - returnsOwner")
    @Test
    void findByLastName() {
        // given
        given(ownerRepository.findByLastName("Stallman")).willReturn(java.util.Optional.ofNullable(owner1));
        // when
        Owner foundOwner = ownerJpaService.findByLastName("Stallman");
        // then
        assertEquals("Stallman", foundOwner.getLastName());
        verify(ownerRepository, times(1)).findByLastName("Stallman");
    }


    @DisplayName(value = "findByLastName - nonExistentLastName - returnsNull")
    @Test
    void findByLastName_nonExistentLastName() {
        // given
        given(ownerRepository.findByLastName("NonExistent")).willReturn(null);
        // when
        // then
        assertThrows(NullPointerException.class, () -> ownerJpaService.findByLastName("NonExistent"));
        verify(ownerRepository, times(1)).findByLastName("NonExistent");
    }

    @DisplayName(value = "findAllByLastNameLikeIgnoreCase_existent - existentLastName - returnsEmptySet")
    @Test
    void findAllByLastNameLikeIgnoreCase_existent() {
        // given
        Set<Owner> returnedFoundOwners = new HashSet<>();
        returnedFoundOwners.add(owner1);
        returnedFoundOwners.add(owner2);
        given(ownerRepository.findAllByLastNameContainingIgnoreCase("Stallman")).willReturn(returnedFoundOwners);
        // when
        Set<Owner> foundOwners = ownerJpaService.findAllByLastNameLikeIgnoreCase("Stallman");
        // then
        assertEquals(2, foundOwners.size());
        verify(ownerRepository, times(1)).findAllByLastNameContainingIgnoreCase("Stallman");
    }

    @DisplayName(value = "findAllByLastNameLikeIgnoreCase_nonExistent - nonExistentLastName - returnsEmptySet")
    @Test
    void findAllByLastNameLikeIgnoreCase_nonExistent() {
        // given
        given(ownerRepository.findAllByLastNameContainingIgnoreCase("NonExistent")).willReturn(new HashSet<Owner>());
        // when
        Set<Owner> foundOwners = ownerJpaService.findAllByLastNameLikeIgnoreCase("NonExistent");
        // then
        assertThat(foundOwners).isEmpty();
        verify(ownerRepository, times(1)).findAllByLastNameContainingIgnoreCase("NonExistent");
    }

    @Test
    void delete() {
        // given
        // when
        ownerJpaService.delete(owner1);
        // then
        verify(ownerRepository, times(1)).delete(any(Owner.class));
    }

    @DisplayName(value = "deleteById - existentId - deleteTheOwner")
    @Test
    void deleteById() {
        // given
        // when
        ownerJpaService.deleteById(1L);
        // then
        verify(ownerRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void save() {
        // given
        given(ownerRepository.save(owner1)).willReturn(owner1);
        // when
        Owner savedOwner = ownerJpaService.save(owner1);
        // then
        assertNotNull(savedOwner);
        verify(ownerRepository, times(1)).save(ArgumentMatchers.any(Owner.class));
    }

}