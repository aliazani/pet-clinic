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
import java.util.HashSet;
import java.util.Set;

@Controller
public class VisitController {
    public static final String VIEW_PET_CREATE_OR_UPDATE_FORM = "pets/createOrUpdateVisitForm";
    private final VisitService visitService;
    private final PetService petService;
    private final PetMapper petMapper;
    private final VisitMapper visitMapper;

    public VisitController(VisitService visitService, PetService petService, PetMapper petMapper, VisitMapper visitMapper) {
        this.visitService = visitService;
        this.petService = petService;
        this.petMapper = petMapper;
        this.visitMapper = visitMapper;
    }

    @ModelAttribute("visitDto")
    public VisitDto loadPetWithVisit(@PathVariable("petId") Long petId, Model model) {
        Pet pet = petService.findById(petId);

        Set<VisitDto> visitDtos = new HashSet<>();
        pet.getVisits().forEach(visit -> visitDtos.add(visitMapper.visitToVisitDto(visit)));
        PetDto petDto = petMapper.petToPetDto(pet);
        petDto.setVisits(visitDtos);
       
        model.addAttribute("petDto", petDto);
        VisitDto visitDto = VisitDto.builder().build();
        visitDto.setPet(petDto);

        return visitDto;
    }

    @GetMapping("/owners/*/pets/{petId}/visits/new")
    public String initNewVisitForm() {
        return VIEW_PET_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/owners/{ownerId}/pets/{petId}/visits/new")
    public String processNewVisitForm(@Valid @ModelAttribute("visitDto") VisitDto visitDto, BindingResult result) {
        if (result.hasErrors())
            return VIEW_PET_CREATE_OR_UPDATE_FORM;
        else {
            Visit visit = visitMapper.visitDtoToVisit(visitDto);
            visitService.save(visit);
            return "redirect:/owners/{ownerId}";
        }
    }
}
