package com.learning.petclinic.controller;

import com.learning.petclinic.dto.OwnerDto;
import com.learning.petclinic.dto.PetDto;
import com.learning.petclinic.dto.PetTypeDto;
import com.learning.petclinic.dto.VisitDto;
import com.learning.petclinic.mapper.OwnerMapper;
import com.learning.petclinic.mapper.PetMapper;
import com.learning.petclinic.mapper.PetTypeMapper;
import com.learning.petclinic.mapper.VisitMapper;
import com.learning.petclinic.model.Owner;
import com.learning.petclinic.model.Pet;
import com.learning.petclinic.service.OwnerService;
import com.learning.petclinic.service.PetService;
import com.learning.petclinic.service.PetTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/owners/{ownerId}/pets")
public class PetController {
    public static final String VIEWS_PETS_CREATE_OR_UPDATE_FORM = "pets/createOrUpdatePetForm";
    private final PetService petService;
    private final PetTypeService petTypeService;
    private final OwnerService ownerService;
    private final OwnerMapper ownerMapper;
    private final PetMapper petMapper;
    private final PetTypeMapper petTypeMapper;
    private final VisitMapper visitMapper;

    public PetController(PetService petService, PetTypeService petTypeService
            , OwnerService ownerService, OwnerMapper ownerMapper, PetMapper petMapper, PetTypeMapper petTypeMapper, VisitMapper visitMapper) {
        this.petService = petService;
        this.petTypeService = petTypeService;
        this.ownerService = ownerService;
        this.ownerMapper = ownerMapper;
        this.petMapper = petMapper;
        this.petTypeMapper = petTypeMapper;
        this.visitMapper = visitMapper;
    }

    @ModelAttribute("petTypeDtos")
    public Set<PetTypeDto> populatePetTypes() {
        Set<PetTypeDto> petTypeDtos = new HashSet<>();
        petTypeService.findAll().forEach(petType -> petTypeDtos.add(petTypeMapper.petTypeToPetTypeDto(petType)));

        return petTypeDtos;
    }

    @ModelAttribute("ownerDto")
    public OwnerDto findOwner(@PathVariable("ownerId") Long ownerId) {
        Owner owner = ownerService.findById(ownerId);
        OwnerDto ownerDto = ownerMapper.ownerToOwnerDto(owner);

        Set<PetDto> petDtos = new HashSet<>();
        owner.getPets().forEach(pet -> {
            Set<VisitDto> visitDtos = new HashSet<>();
            pet.getVisits().forEach(visit -> visitDtos.add(visitMapper.visitToVisitDto(visit)));
            PetDto petDto = petMapper.petToPetDto(pet);
            petDto.setVisits(visitDtos);
            petDtos.add(petDto);
        });
        ownerDto.setPets(petDtos);

        return ownerDto;
    }

    @GetMapping("/new")
    public String initCreationForm(@ModelAttribute("ownerDto") OwnerDto ownerDto, Model model) {
        PetDto petDto = PetDto.builder().build();
        petDto.setOwner(ownerDto);
        model.addAttribute("petDto", petDto);

        return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/new")
    public String processCreationForm(@ModelAttribute("ownerDto") OwnerDto ownerDto,
                                      @ModelAttribute("petDto") @Valid PetDto petDto,
                                      BindingResult result, Model model) {
        if ((!petDto.getName().isEmpty()) &&
                petDto.isNew() &&
                Boolean.TRUE.equals(
                        ownerDto.getPets().stream().map(petDto1 -> petDto1.getName().equals(petDto.getName()))
                                .reduce(false, (hasSameName1, hasSameName2) -> hasSameName1 && hasSameName2)
                )
        ) {
            result.rejectValue("name", "duplicate", "already exists");
        }

        ownerDto.getPets().add(petDto);
        if (result.hasErrors()) {
            model.addAttribute("petDto", petDto);

            return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
        } else {
            Owner owner = ownerMapper.ownerDtoToOwner(ownerDto);
            Pet pet = petMapper.petDtoToPet(petDto);
            pet.setOwner(owner);
            petService.save(pet);

            return "redirect:/owners/" + ownerDto.getId();
        }
    }

    @GetMapping("/{petId}/edit")
    public String initUpdateForm(@PathVariable("petId") Long petId, Model model) {
        Pet pet = petService.findById(petId);
        Set<VisitDto> visitDtos = new HashSet<>();
        pet.getVisits().forEach(visit -> visitDtos.add(visitMapper.visitToVisitDto(visit)));
        PetDto petDto = petMapper.petToPetDto(pet);
        petDto.setVisits(visitDtos);
        model.addAttribute("petDto", petDto);

        return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
    }


    @PostMapping("{petId}/edit")
    public String processUpdateForm(@ModelAttribute("ownerDto") OwnerDto ownerDto,
                                    @ModelAttribute("petDto") @Valid PetDto petDto,
                                    @PathVariable("petId") Long petId,
                                    BindingResult result, Model model) {
        if (result.hasErrors()) {
            petDto.setOwner(ownerDto);
            model.addAttribute("petDto", petDto);

            return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
        } else {
            petDto.setId(petId);
            Pet pet = petMapper.petDtoToPet(petDto);
            Owner owner = ownerMapper.ownerDtoToOwner(ownerDto);
            pet.setOwner(owner);
            petService.save(pet);

            return "redirect:/owners/" + ownerDto.getId();
        }
    }
}
