package com.learning.petclinic.service.map;

import com.learning.petclinic.model.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OwnerMapServiceTest {
    OwnerMapService ownerMapService;
    Owner owner;

    @BeforeEach
    void setUp() {
        ownerMapService = new OwnerMapService(new PetMapService(new VisitMapService()), new PetTypeMapService());

        owner = Owner.builder()
                .firstName("John")
                .lastName("Steven")
                .city("Los Angles")
                .address("43 street.")
                .telephone("123456")
                .build();

        ownerMapService.save(owner);
    }

    @Test
    void findAll() {
        // given
        // when
        Set<Owner> owners = ownerMapService.findAll();
        System.out.println(owners);
        //then
        assertEquals(1, owners.size());
    }

    @Test
    void deleteById() {
        // given
        Long id = owner.getId();
        // when
        ownerMapService.deleteById(id);
        //then
        assertEquals(0, ownerMapService.findAll().size());
    }

    @Test
    void delete() {
        // given
        // when
        ownerMapService.delete(owner);
        // then
        assertEquals(0, ownerMapService.findAll().size());
    }

    @Test
    void save() {
        // given
        Long id = 2L;
        Owner owner = Owner.builder()
                .firstName("Gloria")
                .lastName("Sophia")
                .city("Amsterdam")
                .address("1234 street.")
                .telephone("098765")
                .build();
        // when
        Owner savedOwner = ownerMapService.save(owner);
        // then
        assertEquals(id, savedOwner.getId());

    }

    @Test
    void findById() {
        // given
        Long id = owner.getId();
        // when
        Owner result = ownerMapService.findById(id);
        // then
        assertEquals(1L, result.getId());
        assertEquals("John", result.getFirstName());
        assertEquals("Steven", result.getLastName());
        assertEquals("Los Angles", result.getCity());
        assertEquals("43 street.", result.getAddress());
        assertEquals("123456", result.getTelephone());
    }

    @DisplayName(value = "findByLastName - existentLastName - returnsOwner")
    @Test
    void findByLastName() {
        // given
        String lastName = owner.getLastName();
        // when
        Owner result = ownerMapService.findByLastName(lastName);
        // then
        assertEquals(1L, result.getId());
        assertEquals("John", result.getFirstName());
        assertEquals("Steven", result.getLastName());
        assertEquals("Los Angles", result.getCity());
        assertEquals("43 street.", result.getAddress());
        assertEquals("123456", result.getTelephone());
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
}