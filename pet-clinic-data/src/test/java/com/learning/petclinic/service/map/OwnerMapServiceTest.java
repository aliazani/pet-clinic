package com.learning.petclinic.service.map;

import com.learning.petclinic.model.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class OwnerMapServiceTest {
    OwnerMapService ownerMapService;
    Owner owner1;
    Owner owner2;
    Owner owner3;

    @BeforeEach
    void setUp() {
        ownerMapService = new OwnerMapService(new PetMapService(new VisitMapService()), new PetTypeMapService());

        owner1 = Owner.builder()
                .firstName("John")
                .lastName("Steven")
                .city("Los Angles")
                .address("43 street.")
                .telephone("123456")
                .build();

        owner2 = Owner.builder()
                .firstName("Mary")
                .lastName("Steven")
                .city("Chicago")
                .address("432 street.")
                .telephone("1234576")
                .build();

        owner3 = Owner.builder()
                .firstName("Robert")
                .lastName("Flex")
                .city("Amsterdam")
                .address("Love street.")
                .telephone("121231236")
                .build();

        ownerMapService.save(owner1);
        ownerMapService.save(owner2);
        ownerMapService.save(owner3);
    }

    @Test
    void findAll() {
        // given
        // when
        Set<Owner> owners = ownerMapService.findAll();
        //then
        assertEquals(3, owners.size());
    }

    @DisplayName(value = "findById - existentId - returnsOwner")
    @Test
    void findById() {
        // given
        Long id = owner1.getId();
        // when
        Owner foundOwner = ownerMapService.findById(id);
        // then
        assertEquals(1L, foundOwner.getId());
        assertEquals("John", foundOwner.getFirstName());
        assertEquals("Steven", foundOwner.getLastName());
        assertEquals("Los Angles", foundOwner.getCity());
        assertEquals("43 street.", foundOwner.getAddress());
        assertEquals("123456", foundOwner.getTelephone());
    }

    @DisplayName(value = "findById - nonExistentId - returnsNull")
    @Test
    void findById_nonExistentId() {
        // given
        Long id = Long.MAX_VALUE;
        // when
        Owner foundOwner = ownerMapService.findById(id);
        // then
        assertNull(foundOwner);
    }

    @DisplayName(value = "findByLastName - existentLastName - returnsOwner")
    @Test
    void findByLastName() {
        // given
        String lastName = owner3.getLastName();
        // when
        Owner foundOwner = ownerMapService.findByLastName(lastName);
        // then
        assertEquals(3L, foundOwner.getId());
        assertEquals("Robert", foundOwner.getFirstName());
        assertEquals("Flex", foundOwner.getLastName());
        assertEquals("Amsterdam", foundOwner.getCity());
        assertEquals("Love street.", foundOwner.getAddress());
        assertEquals("121231236", foundOwner.getTelephone());
    }

    @DisplayName(value = "findByLastName - nonExistentLastName - returnsNull")
    @Test
    void findByLastName_nonExistent_returnsNull() {
        // given
        String lastName = "Not Found";
        // when
        Owner result = ownerMapService.findByLastName(lastName);
        // then
        assertNull(result);
    }

    @DisplayName(value = "findAllByLastNameLikeIgnoreCase_existent - existentLastName - returnsOwners")
    @Test
    void findAllByLastNameLikeIgnoreCase_existent() {
        // given
        String lastName = "steven";
        // when
        Set<Owner> result = ownerMapService.findAllByLastNameLikeIgnoreCase(lastName);
        // then
        assertEquals(2, result.size());
    }

    @DisplayName(value = "findAllByLastNameLikeIgnoreCase_nonExistent - nonExistentLastName - returnsEmptySet")
    @Test
    void findAllByLastNameLike_nonExistent() {
        // given
        String lastName = "Not Found";
        // when
        Set<Owner> result = ownerMapService.findAllByLastNameLikeIgnoreCase(lastName);
        // then
        assertThat(result).isEmpty();
    }

    @Test
    void delete() {
        // given
        // when
        ownerMapService.delete(owner1);
        // then
        assertEquals(2, ownerMapService.findAll().size());
    }

    @Test
    void deleteById() {
        // given
        Long id = owner1.getId();
        // when
        ownerMapService.deleteById(id);
        //then
        assertEquals(2, ownerMapService.findAll().size());
    }


    @Test
    void save() {
        // given
        Long id = 4L;
        Owner owner4 = Owner.builder()
                .firstName("Gloria")
                .lastName("Sophia")
                .city("Amsterdam")
                .address("1234 street.")
                .telephone("098765")
                .build();
        // when
        Owner savedOwner = ownerMapService.save(owner4);
        // then
        assertEquals(id, savedOwner.getId());
    }
}