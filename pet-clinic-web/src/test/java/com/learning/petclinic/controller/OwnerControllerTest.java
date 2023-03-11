package com.learning.petclinic.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learning.petclinic.dto.OwnerDto;
import com.learning.petclinic.dto.PetDto;
import com.learning.petclinic.mapper.OwnerMapper;
import com.learning.petclinic.mapper.PetMapper;
import com.learning.petclinic.model.Owner;
import com.learning.petclinic.model.Pet;
import com.learning.petclinic.service.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
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
    PetMapper petMapper;

    @Mock
    Model model;

    ObjectMapper objectMapper;

    @InjectMocks
    OwnerController ownerController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
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
        Pet pet1 = Pet.builder()
                .name("Gorge")
                .birthDate(LocalDate.now())
                .build();

        PetDto petDto1 = PetDto.builder()
                .name(pet1.getName())
                .birthDate(pet1.getBirthDate())
                .build();
        Set<Pet> owner1Pets = new HashSet<>();
        owner1Pets.add(pet1);

        Set<Owner> oneOwner = new HashSet<>();
        Owner owner1 = Owner.builder()
                .id(ownerDto1.getId())
                .firstName(ownerDto1.getFirstName())
                .lastName(ownerDto1.getLastName())
                .city(ownerDto1.getCity())
                .address(ownerDto1.getAddress())
                .telephone(ownerDto1.getTelephone())
                .pets(owner1Pets)
                .build();
        oneOwner.add(owner1);
        given(ownerService.findAllByLastNameLikeIgnoreCase("dow")).willReturn(oneOwner);
        given(ownerMapper.ownerToOwnerDto(owner1)).willReturn(ownerDto1);
        given(petMapper.petToPetDto(pet1)).willReturn(petDto1);
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
        Pet pet1 = Pet.builder()
                .name("Gorge")
                .birthDate(LocalDate.now())
                .build();

        PetDto petDto1 = PetDto.builder()
                .name(pet1.getName())
                .birthDate(pet1.getBirthDate())
                .build();

        Pet pet2 = Pet.builder()
                .name("Kitty")
                .birthDate(LocalDate.now())
                .build();

        PetDto petDto2 = PetDto.builder()
                .name(pet2.getName())
                .birthDate(pet2.getBirthDate())
                .build();

        Set<Pet> owner2Pets = new HashSet<>();
        owner2Pets.add(pet1);

        Set<Pet> owner3Pets = new HashSet<>();
        owner3Pets.add(pet2);

        Set<Owner> manyOwners = new HashSet<>();
        Owner owner2 = Owner.builder()
                .id(ownerDto2.getId())
                .firstName(ownerDto2.getFirstName())
                .lastName(ownerDto2.getLastName())
                .city(ownerDto2.getCity())
                .address(ownerDto2.getAddress())
                .telephone(ownerDto2.getTelephone())
                .pets(owner2Pets)
                .build();
        Owner owner3 = Owner.builder()
                .id(ownerDto3.getId())
                .firstName(ownerDto3.getFirstName())
                .lastName(ownerDto3.getLastName())
                .city(ownerDto3.getCity())
                .address(ownerDto3.getAddress())
                .telephone(ownerDto3.getTelephone())
                .pets(owner3Pets)
                .build();
        manyOwners.add(owner2);
        manyOwners.add(owner3);
        given(ownerService.findAllByLastNameLikeIgnoreCase("for")).willReturn(manyOwners);
        given(ownerMapper.ownerToOwnerDto(owner2)).willReturn(ownerDto2);
        given(ownerMapper.ownerToOwnerDto(owner3)).willReturn(ownerDto3);
        given(petMapper.petToPetDto(pet1)).willReturn(petDto1);
        given(petMapper.petToPetDto(pet2)).willReturn(petDto2);
        // when
        // then
        mockMvc.perform(get("/owners?lastName=" + ownerDto2.getLastName()))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/ownersList"))
                .andExpect(model().attribute("ownerDtos", hasSize(2)))
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
        given(ownerMapper.ownerToOwnerDto(owner1)).willReturn(ownerDto1);
        // when
        // then
        mockMvc.perform(get("/owners/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/ownerDetails"))
                .andExpect(model().attribute("ownerDto",
                        hasProperty("id", is(ownerDto1.getId()))))
                .andExpect(model().attribute("ownerDto",
                        hasProperty("firstName", is(ownerDto1.getFirstName()))))
                .andExpect(model().attribute("ownerDto",
                        hasProperty("lastName", is(ownerDto1.getLastName()))))
                .andExpect(model().attribute("ownerDto",
                        hasProperty("city", is(ownerDto1.getCity()))))
                .andExpect(model().attribute("ownerDto",
                        hasProperty("telephone", is(ownerDto1.getTelephone()))))
                .andExpect(model().attribute("ownerDto",
                        hasProperty("address", is(ownerDto1.getAddress()))))
        ;

        verify(ownerService, times(1)).findById(1L);
    }

    @Test
    void initCreationFrom() throws Exception {
        // Given
        // When
        // Then
        mockMvc.perform(get("/owners/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/createOrUpdateOwnerForm"))
                .andExpect(model().attributeExists("ownerDto"));

        verifyNoInteractions(ownerService);
    }

    @Test
    void processCreationForm() throws Exception {
        // Given
        OwnerDto ownerDto4 = OwnerDto.builder()
                .firstName("Lora")
                .lastName("Jackson")
                .city("Berlin")
                .address("921 street.")
                .telephone("0923712")
                .build();

        Owner owner4 = Owner.builder()
                .firstName(ownerDto4.getFirstName())
                .lastName(ownerDto4.getLastName())
                .city(ownerDto4.getCity())
                .address(ownerDto4.getAddress())
                .telephone(ownerDto4.getTelephone())
                .build();

        given(ownerMapper.ownerDtoToOwner(any())).willReturn(owner4);
        given(ownerService.save(owner4)).willReturn(owner4);
        // When
        // Then
        mockMvc.perform(post("/owners/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(owner4))
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/" + owner4.getId()))
                .andExpect(model().attributeExists("ownerDto"));

        verify(ownerService, times(1)).save(owner4);
    }

    @Test
    void initUpdateFrom() throws Exception {
        // Given
        Owner owner4 = Owner.builder()
                .id(4L)
                .firstName("Rob")
                .lastName("For")
                .city("Amsterdam")
                .address("821 street.")
                .telephone("21232890")
                .build();

        OwnerDto ownerDto4 = OwnerDto.builder()
                .id(owner4.getId())
                .firstName(owner4.getFirstName())
                .lastName(owner4.getLastName())
                .city(owner4.getCity())
                .address(owner4.getAddress())
                .telephone(owner4.getTelephone())
                .build();

        given(ownerService.findById(owner4.getId())).willReturn(owner4);
        given(ownerMapper.ownerToOwnerDto(owner4)).willReturn(ownerDto4);
        // When
        // Then
        mockMvc.perform(get("/owners/" + owner4.getId() + "/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/createOrUpdateOwnerForm"))
                .andExpect(model().attributeExists("ownerDto"));

        verify(ownerService, times(1)).findById(owner4.getId());
    }

    @Test
    void processUpdateForm() throws Exception {
        // Given
        Owner updatedOwner3 = Owner.builder()
                .id(3L)
                .firstName("Michel")
                .lastName("Fow")
                .city("Amsterdam")
                .address("19 street.")
                .telephone("2123123890")
                .build();

        given(ownerMapper.ownerDtoToOwner(any())).willReturn(updatedOwner3);
        given(ownerService.save(updatedOwner3)).willReturn(updatedOwner3);
        // When
        // Then
        mockMvc.perform(post("/owners/" + updatedOwner3.getId() + "/edit/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedOwner3))
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/" + updatedOwner3.getId()))
                .andExpect(model().attributeExists("ownerDto"));

        verify(ownerService, times(1)).save(updatedOwner3);
    }
}