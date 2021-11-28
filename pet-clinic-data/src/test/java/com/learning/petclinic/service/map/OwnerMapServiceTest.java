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

    @DisplayName(value = "findById - existentId - returnsOwner")
    @Test
    void findById() {
        // given
        Long id = owner.getId();
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
        String lastName = owner.getLastName();
        // when
        Owner foundOwner = ownerMapService.findByLastName(lastName);
        // then
        assertEquals(1L, foundOwner.getId());
        assertEquals("John", foundOwner.getFirstName());
        assertEquals("Steven", foundOwner.getLastName());
        assertEquals("Los Angles", foundOwner.getCity());
        assertEquals("43 street.", foundOwner.getAddress());
        assertEquals("123456", foundOwner.getTelephone());
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

    @Test
    void delete() {
        // given
        // when
        ownerMapService.delete(owner);
        // then
        assertEquals(0, ownerMapService.findAll().size());
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
    void save() {
        // given
        Long id = 2L;
        Owner owner2 = Owner.builder()
                .firstName("Gloria")
                .lastName("Sophia")
                .city("Amsterdam")
                .address("1234 street.")
                .telephone("098765")
                .build();
        // when
        Owner savedOwner = ownerMapService.save(owner2);
        // then
        assertEquals(id, savedOwner.getId());
    }


}