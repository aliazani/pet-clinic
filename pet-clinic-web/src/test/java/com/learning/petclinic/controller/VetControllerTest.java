package com.learning.petclinic.controller;

import com.learning.petclinic.model.Speciality;
import com.learning.petclinic.model.Vet;
import com.learning.petclinic.service.VetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class VetControllerTest {
    Set<Vet> vets;
    Vet vet1;
    Vet vet2;

    @Mock
    VetService vetService;

    @Mock
    Model model;

    @InjectMocks
    VetController vetController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        Speciality speciality = new Speciality();
        speciality.setName("Surgery");
        Set<Speciality> specialities = new HashSet<>();
        specialities.add(speciality);

        vet1 = Vet.builder()
                .firstName("Majid")
                .lastName("Samie")
                .specialities(specialities)
                .build();

        vet2 = Vet.builder()
                .firstName("Reza")
                .lastName("Steven")
                .specialities(specialities)
                .build();

        vets = new HashSet<>();
        vets.add(vet1);
        vets.add(vet2);

        mockMvc = MockMvcBuilders.standaloneSetup(vetController).build();
    }

    @ParameterizedTest
    @ValueSource(strings = {"/vets/", "/vets/vetList", "/vets/vetList.html"})
    void listVets(String path) throws Exception {
        // given
        given(vetService.findAll()).willReturn(vets);
        // when
        // then
        mockMvc.perform(get(path))
                .andExpect(status().isOk())
                .andExpect(view().name("vets/vetList"))
                .andExpect(model().attribute("vets", hasSize(2)))
        ;
    }
}