package com.learning.petclinic.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learning.petclinic.dto.OwnerDto;
import com.learning.petclinic.dto.PetDto;
import com.learning.petclinic.dto.PetTypeDto;
import com.learning.petclinic.mapper.OwnerMapper;
import com.learning.petclinic.mapper.PetMapper;
import com.learning.petclinic.mapper.PetTypeMapper;
import com.learning.petclinic.model.Owner;
import com.learning.petclinic.model.Pet;
import com.learning.petclinic.model.PetType;
import com.learning.petclinic.service.OwnerService;
import com.learning.petclinic.service.PetService;
import com.learning.petclinic.service.PetTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class PetControllerTest {
    Owner owner1;
    OwnerDto ownerDto1;
    Set<PetType> petTypes;
    PetType dog;
    PetType cat;
    PetTypeDto dogDto;
    PetTypeDto catDto;
    Pet pet1;
    PetDto petDto1;

    ObjectMapper objectMapper;

    @Mock
    PetService petService;

    @Mock
    PetTypeService petTypeService;

    @Mock
    OwnerService ownerService;

    @Mock
    OwnerMapper ownerMapper;

    @Mock
    PetMapper petMapper;

    @Mock
    PetTypeMapper petTypeMapper;

    @InjectMocks
    PetController petController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        owner1 = Owner.builder()
                .id(1L)
                .firstName("John")
                .lastName("Dow")
                .city("Michigan")
                .address("43 street.")
                .telephone("123456")
                .build();

        ownerDto1 = OwnerDto.builder()
                .id(owner1.getId())
                .firstName(owner1.getFirstName())
                .lastName(owner1.getLastName())
                .city(owner1.getCity())
                .address(owner1.getAddress())
                .telephone(owner1.getTelephone())
                .build();

        dog = PetType.builder()
                .name("Dog")
                .build();

        dogDto = PetTypeDto.builder()
                .name(dog.getName())
                .build();

        cat = PetType.builder()
                .name("Cat")
                .build();

        catDto = PetTypeDto.builder()
                .name(cat.getName())
                .build();

        petTypes = new HashSet<>();
        petTypes.add(dog);
        petTypes.add(cat);

        pet1 = Pet.builder()
                .id(1L)
                .name("Kitty")
                .birthDate(LocalDate.now())
                .petType(cat)
                .build();

        petDto1 = PetDto.builder()
                .id(pet1.getId())
                .name(pet1.getName())
                .birthDate(pet1.getBirthDate())
                .petType(catDto)
                .build();

        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();

        mockMvc = MockMvcBuilders.standaloneSetup(petController).build();

        owner1.setPets(Stream.of(pet1).collect(Collectors.toSet()));
        given(ownerMapper.ownerToOwnerDto(owner1)).willReturn(ownerDto1);
        given(petMapper.petToPetDto(pet1)).willReturn(petDto1);
        given(ownerService.findById(owner1.getId())).willReturn(owner1);
    }

    @Test
    void populatePetTypes() {
        // given
        given(petTypeService.findAll()).willReturn(petTypes);
        given(petTypeMapper.petTypeToPetTypeDto(cat)).willReturn(catDto);
        given(petTypeMapper.petTypeToPetTypeDto(dog)).willReturn(dogDto);
        // when
        Set<PetTypeDto> petTypeDtos = petController.populatePetTypes();
        // then
        assertEquals(2, petTypeDtos.size());
    }


    @Test
    void initCreationForm() throws Exception {
        // Given
        owner1.setPets(Stream.of(pet1).collect(Collectors.toSet()));
        // When
        // Then
        mockMvc.perform(get("/owners/" + owner1.getId() + "/pets/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("ownerDto"))
                .andExpect(model().attributeExists("petDto"))
                .andExpect(view().name("pets/createOrUpdatePetForm"))
        ;
    }

    @Test
    void processCreationForm() throws Exception {
        // Given
        given(ownerMapper.ownerDtoToOwner(ownerDto1)).willReturn(owner1);
        given(petMapper.petDtoToPet(petDto1)).willReturn(pet1);
        // When
        // Then
        mockMvc.perform(post("/owners/" + owner1.getId() + "/pets/new")
                        .flashAttr("petDto", petDto1)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/" + owner1.getId()));

        verify(petService, times(1)).save(pet1);
    }

    @Test
    void initUpdateForm() throws Exception {
        // Given
        given(petService.findById(pet1.getId())).willReturn(pet1);
        // When
        // Then
        mockMvc.perform(get("/owners/" + owner1.getId() + "/pets/" + pet1.getId() + "/edit"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("petDto"))
                .andExpect(view().name("pets/createOrUpdatePetForm"))
        ;
    }

    @Test
    void processUpdateForm() throws Exception {
        // Given
        PetDto updatedPetDto1 = petDto1;
        updatedPetDto1.setName("Gorge");
        given(ownerMapper.ownerDtoToOwner(ownerDto1)).willReturn(owner1);
        given(petMapper.petDtoToPet(petDto1)).willReturn(pet1);
        // When
        // Then
        mockMvc.perform(post("/owners/" + owner1.getId() + "/pets/" + pet1.getId() + "/edit")
                        .flashAttr("petDto", updatedPetDto1))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().attributeExists("petDto"))
                .andExpect(model().attributeExists("ownerDto"))
                .andExpect(view().name("redirect:/owners/" + owner1.getId()));

        verify(petService, times(1)).save(pet1);
    }
}