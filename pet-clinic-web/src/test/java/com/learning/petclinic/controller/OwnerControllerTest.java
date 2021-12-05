package com.learning.petclinic.controller;

import com.learning.petclinic.dto.OwnerDto;
import com.learning.petclinic.mapper.OwnerMapper;
import com.learning.petclinic.model.Owner;
import com.learning.petclinic.service.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {
    Set<OwnerDto> ownerDtos;
    OwnerDto ownerDto1;
    OwnerDto ownerDto2;
    OwnerDto ownerDto3;

    @Mock
    OwnerService ownerService;

    @Mock
    OwnerMapper ownerMapper;

    @Mock
    Model model;

    @InjectMocks
    OwnerController ownerController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        ownerDtos = new HashSet<>();

        ownerDto1 = OwnerDto.builder()
                .id(1L)
                .firstName("John")
                .lastName("Dow")
                .city("Michigan")
                .address("43 street.")
                .telephone("123456")
                .build();

        ownerDto2 = OwnerDto.builder()
                .id(2L)
                .firstName("Steven")
                .lastName("For")
                .city("Tehran")
                .address("Enghelab street.")
                .telephone("1234567890")
                .build();

        ownerDto3 = OwnerDto.builder()
                .id(3L)
                .firstName("Rob")
                .lastName("For")
                .city("Amsterdam")
                .address("821 street.")
                .telephone("21232890")
                .build();

        ownerDtos.add(ownerDto1);
        ownerDtos.add(ownerDto2);
        ownerDtos.add(ownerDto3);

        mockMvc = MockMvcBuilders.standaloneSetup(ownerController).build();
    }

    @Test
    void findOwners() throws Exception {
        // given
        // when
        // then
        mockMvc.perform(get("/owners/find"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/findOwners"))
                .andExpect(model().attributeExists("ownerDto"))
        ;
        verifyNoInteractions(ownerService);
    }

    @DisplayName("findOwners - whenCalled - returnsOneOwner")
    @Test
    void processFindForm_returnsOneOwner() throws Exception {
        // given
        Set<Owner> oneOwner = new HashSet<>();
        Owner owner1 = Owner.builder()
                .id(ownerDto1.getId())
                .firstName(ownerDto1.getFirstName())
                .lastName(ownerDto1.getLastName())
                .city(ownerDto1.getCity())
                .address(ownerDto1.getAddress())
                .telephone(ownerDto1.getTelephone())
                .build();
        oneOwner.add(owner1);
        given(ownerService.findAllByLastNameLikeIgnoreCase("dow")).willReturn(oneOwner);
        given(ownerMapper.ownerToOwnerDto(owner1)).willReturn(ownerDto1);
        // when
        // then
        mockMvc.perform(get("/owners?lastName=" + ownerDto1.getLastName()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/" + ownerDto1.getId()))
        ;

        verify(ownerService, times(1)).findAllByLastNameLikeIgnoreCase("dow");
    }

    @DisplayName("findOwners - whenCalled - returnsManyOwners")
    @Test
    void processFindForm_returnsManyOwner() throws Exception {
        // given
        Set<Owner> manyOwners = new HashSet<>();
        Owner owner2 = Owner.builder()
                .id(ownerDto2.getId())
                .firstName(ownerDto2.getFirstName())
                .lastName(ownerDto2.getLastName())
                .city(ownerDto2.getCity())
                .address(ownerDto2.getAddress())
                .telephone(ownerDto2.getTelephone())
                .build();
        Owner owner3 = Owner.builder()
                .id(ownerDto3.getId())
                .firstName(ownerDto3.getFirstName())
                .lastName(ownerDto3.getLastName())
                .city(ownerDto3.getCity())
                .address(ownerDto3.getAddress())
                .telephone(ownerDto3.getTelephone())
                .build();
        manyOwners.add(owner2);
        manyOwners.add(owner3);
        given(ownerService.findAllByLastNameLikeIgnoreCase("for")).willReturn(manyOwners);
        // when
        // then
        mockMvc.perform(get("/owners?lastName=" + ownerDto2.getLastName()))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/ownersList"))
                .andExpect(model().attribute("owners", hasSize(2)))
        ;
        verify(ownerService, times(1)).findAllByLastNameLikeIgnoreCase("for");
    }

    @Test
    void showOwner() throws Exception {
        // given
        Owner owner1 = Owner.builder()
                .id(ownerDto1.getId())
                .firstName(ownerDto1.getFirstName())
                .lastName(ownerDto1.getLastName())
                .city(ownerDto1.getCity())
                .address(ownerDto1.getAddress())
                .telephone(ownerDto1.getTelephone())
                .build();

        given(ownerService.findById(1L)).willReturn(owner1);
        // when
        // then
        mockMvc.perform(get("/owners/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/ownerDetails"))
                .andExpect(model().attribute("owner",
                        hasProperty("id", is(ownerDto1.getId()))))
                .andExpect(model().attribute("owner",
                        hasProperty("firstName", is(ownerDto1.getFirstName()))))
                .andExpect(model().attribute("owner",
                        hasProperty("lastName", is(ownerDto1.getLastName()))))
                .andExpect(model().attribute("owner",
                        hasProperty("city", is(ownerDto1.getCity()))))
                .andExpect(model().attribute("owner",
                        hasProperty("telephone", is(ownerDto1.getTelephone()))))
                .andExpect(model().attribute("owner",
                        hasProperty("address", is(ownerDto1.getAddress()))))
        ;

        verify(ownerService, times(1)).findById(1L);
    }
}