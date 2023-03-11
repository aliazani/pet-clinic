package com.learning.petclinic.controller;

import com.learning.petclinic.dto.OwnerDto;
import com.learning.petclinic.dto.PetDto;
import com.learning.petclinic.dto.VisitDto;
import com.learning.petclinic.mapper.PetMapper;
import com.learning.petclinic.mapper.VisitMapper;
import com.learning.petclinic.model.Pet;
import com.learning.petclinic.model.Visit;
import com.learning.petclinic.service.PetService;
import com.learning.petclinic.service.VisitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.time.LocalDate;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class VisitControllerTest {
    OwnerDto ownerDto1;
    Pet pet1;
    PetDto petDto1;
    VisitDto visitDto1;
    Visit visit1;

    @Mock
    PetService petService;

    @Mock
    VisitService visitService;

    @Mock
    PetMapper petMapper;

    @Mock
    VisitMapper visitMapper;

    @InjectMocks
    VisitController visitController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        ownerDto1 = OwnerDto.builder()
                .id(1L)
                .firstName("John")
                .lastName("Dow")
                .city("Michigan")
                .address("43 street.")
                .telephone("123456")
                .build();

        pet1 = Pet.builder()
                .id(1L)
                .name("Kitty")
                .birthDate(LocalDate.now())
                .build();

        petDto1 = PetDto.builder()
                .id(pet1.getId())
                .name(pet1.getName())
                .birthDate(pet1.getBirthDate())
                .build();

        visit1 = Visit.builder()
                .pet(pet1)
                .date(LocalDate.now())
                .description("Health check.")
                .build();

        visitDto1 = VisitDto.builder()
                .pet(petDto1)
                .date(LocalDate.now())
                .description("Health check.")
                .build();

        mockMvc = MockMvcBuilders.standaloneSetup(visitController).build();
    }

    @Test
    void loadPetWithVisit() {
        // given
        given(petService.findById(pet1.getId())).willReturn(pet1);
        given(petMapper.toDTO(pet1)).willReturn(petDto1);
        // when
        visitController.loadPetWithVisit(pet1.getId());
        // then
        verify(petService, times(1)).findById(pet1.getId());
    }

    @Test
    void initNewVisitForm() throws Exception {
        mockMvc.perform(get("/owners/*/pets/" + petDto1.getId() + "/visits/new")
                        .flashAttr("petDto", petDto1)
                ).andExpect(view().name("pets/createOrUpdateVisitForm"))
                .andExpect(model().attribute("petDto",
                        hasProperty("id", is(petDto1.getId()))
                ))
                .andExpect(model().attribute("petDto",
                        hasProperty("name", is(petDto1.getName()))
                ))
                .andExpect(model().attributeExists("visitDto"));
    }

    @Test
    void processNewVisitForm() throws Exception {
        // given
        given(visitMapper.toEntity(visitDto1)).willReturn(visit1);
        given(visitService.save(visit1)).willReturn(visit1);
        // when
        // then
        mockMvc.perform(post("/owners/" + ownerDto1.getId() + "/pets/" + petDto1.getId() + "/visits/new")
                        .flashAttr("visitDto", visitDto1)
                        .flashAttr("petDto", petDto1)
                )
                .andExpect(view().name("redirect:/owners/{ownerId}"))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().attributeExists("petDto"))
                .andExpect(model().attributeExists("visitDto"));

    }
}