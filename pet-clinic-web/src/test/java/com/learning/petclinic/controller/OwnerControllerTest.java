package com.learning.petclinic.controller;

import com.learning.petclinic.model.Owner;
import com.learning.petclinic.service.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {
    Set<Owner> owners;
    Owner owner1;
    Owner owner2;

    @Mock
    OwnerService ownerService;

    @Mock
    Model model;

    @InjectMocks
    OwnerController ownerController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        owners = new HashSet<>();

        owner1 = Owner.builder()
                .firstName("John")
                .lastName("Dow")
                .city("Michigan")
                .address("43 street.")
                .telephone("123456")
                .build();

        owner2 = Owner.builder()
                .firstName("Steven")
                .lastName("For")
                .city("Tehran")
                .address("Enghelab street.")
                .telephone("1234567890")
                .build();

        owners.add(owner1);
        owners.add(owner2);

        mockMvc = MockMvcBuilders.standaloneSetup(ownerController).build();
    }

    @Test
    void listOwners() throws Exception {
        // given
        given(ownerService.findAll()).willReturn(owners);
        // when
        // then
        mockMvc.perform(get("/owners"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/index"))
                .andExpect(model().attribute("listOwners", hasSize(2)))
        ;
    }

    @Test
    void findOwners() throws Exception {
        // given
        // when
        // then
        mockMvc.perform(get("/owners/find"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/findOwners"));
    }


    @Test
    void showOwner() throws Exception {
        // given
        owner1.setId(1L);
        given(ownerService.findById(1L)).willReturn(owner1);
        // when
        // then
        mockMvc.perform(get("/owners/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/ownerDetails"))
                .andExpect(model().attribute("owner",
                        hasProperty("id", is(owner1.getId()))))
                .andExpect(model().attribute("owner",
                        hasProperty("firstName", is(owner1.getFirstName()))))
                .andExpect(model().attribute("owner",
                        hasProperty("lastName", is(owner1.getLastName()))))
                .andExpect(model().attribute("owner",
                        hasProperty("city", is(owner1.getCity()))))
                .andExpect(model().attribute("owner",
                        hasProperty("telephone", is(owner1.getTelephone()))))
                .andExpect(model().attribute("owner",
                        hasProperty("address", is(owner1.getAddress()))))
        ;
    }
}