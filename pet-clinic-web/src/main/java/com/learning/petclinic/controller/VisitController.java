package com.learning.petclinic.controller;

import com.learning.petclinic.dto.PetDto;
import com.learning.petclinic.dto.VisitDto;
import com.learning.petclinic.mapper.PetMapper;
import com.learning.petclinic.mapper.VisitMapper;
import com.learning.petclinic.model.Pet;
import com.learning.petclinic.model.Visit;
import com.learning.petclinic.service.PetService;
import com.learning.petclinic.service.VisitService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class VisitController {
    public static final String VIEW_PET_CREATE_OR_UPDATE_FORM = "pets/createOrUpdateVisitForm";
    private final VisitService visitService;
    private final PetService petService;
    private final PetMapper petMapper;
    private final VisitMapper visitMapper;

    public VisitController(VisitService visitService, PetService petService,
                           PetMapper petMapper, VisitMapper visitMapper) {
        this.visitService = visitService;
        this.petService = petService;
        this.petMapper = petMapper;
        this.visitMapper = visitMapper;
    }

    @ModelAttribute("petDto")
    public PetDto loadPetWithVisit(@PathVariable("petId") Long petId) {
        Pet pet = petService.findById(petId);

        return petMapper.toDTO(pet);
    }

    @GetMapping("/owners/*/pets/{petId}/visits/new")
    public String initNewVisitForm(Model model, @ModelAttribute("petDto") PetDto petDto) {
        VisitDto visitDto = VisitDto.builder()
                .pet(petDto)
                .build();
        model.addAttribute("visitDto", visitDto);

        return VIEW_PET_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/owners/{ownerId}/pets/{petId}/visits/new")
    public String processNewVisitForm(@Valid @ModelAttribute("visitDto") VisitDto visitDto,
                                      @ModelAttribute("petDto") PetDto petDto,
                                      BindingResult result) {
        visitDto.setPet(petDto);
        if (result.hasErrors())
            return VIEW_PET_CREATE_OR_UPDATE_FORM;
        else {
            Visit visit = visitMapper.toEntity(visitDto);
            visitService.save(visit);

            return "redirect:/owners/{ownerId}";
        }
    }
}
