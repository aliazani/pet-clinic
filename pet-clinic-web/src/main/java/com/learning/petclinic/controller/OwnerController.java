package com.learning.petclinic.controller;

import com.learning.petclinic.dto.OwnerDto;
import com.learning.petclinic.dto.PetDto;
import com.learning.petclinic.dto.VisitDto;
import com.learning.petclinic.mapper.OwnerMapper;
import com.learning.petclinic.mapper.PetMapper;
import com.learning.petclinic.mapper.VisitMapper;
import com.learning.petclinic.model.Owner;
import com.learning.petclinic.service.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/owners")
public class OwnerController {
    public static final String VIEWS_OWNER_CREATE_OR_UPDATE_FORM = "owners/createOrUpdateOwnerForm";
    public static final String VIEWS_OWNER_DETAILS = "owners/ownerDetails";
    public static final String VIEWS_OWNER_FIND_OWNERS = "owners/findOwners";
    public static final String VIEWS_OWNER_LIST = "owners/ownersList";
    private final OwnerService ownerService;
    private final PetMapper petMapper;
    private final OwnerMapper ownerMapper;
    private final VisitMapper visitMapper;

    public OwnerController(OwnerService ownerService, PetMapper petMapper, OwnerMapper ownerMapper, VisitMapper visitMapper) {
        this.ownerService = ownerService;
        this.petMapper = petMapper;
        this.ownerMapper = ownerMapper;
        this.visitMapper = visitMapper;
    }

    @GetMapping("/find")
    public String findOwners(Model model) {
        model.addAttribute("ownerDto", OwnerDto.builder().build());

        return VIEWS_OWNER_FIND_OWNERS;
    }

    @GetMapping
    public String processFindForm(@ModelAttribute("ownerDto") OwnerDto ownerDto, BindingResult result, Model model) {
        if (ownerDto.getLastName() == null)
            ownerDto.setLastName("");
        Set<OwnerDto> results = new HashSet<>();
        ownerService.findAllByLastNameLikeIgnoreCase(ownerDto.getLastName().strip().toLowerCase())
                .forEach(owner -> {
                            OwnerDto ownerDto1 = ownerMapper.ownerToOwnerDto(owner);
                            Set<PetDto> petDtos = new HashSet<>();
                            owner.getPets().forEach(pet -> {
                                Set<VisitDto> visitDtos = new HashSet<>();
                                pet.getVisits().forEach(visit -> visitDtos.add(visitMapper.visitToVisitDto(visit)));
                                PetDto petDto = petMapper.petToPetDto(pet);
                                petDto.setVisits(visitDtos);
                                petDtos.add(petDto);
                            });

                            ownerDto1.setPets(petDtos);
                            results.add(ownerDto1);

                        }
                );

        if (results.isEmpty()) {
            result.rejectValue("lastName", "notFound", "notFound");

            return VIEWS_OWNER_FIND_OWNERS;
        } else if (results.size() == 1) {
            ownerDto = results.iterator().next();
            ownerDto.setId(ownerDto.getId());

            return "redirect:/owners/" + ownerDto.getId();
        } else {
            model.addAttribute("ownerDtos", results);

            return VIEWS_OWNER_LIST;
        }
    }

    @GetMapping("/{ownerId}")
    public String showOwner(@PathVariable("ownerId") Long id, Model model) {
        return findOwner(id, model, VIEWS_OWNER_DETAILS);
    }

    @GetMapping("/new")
    public String initCreationFrom(Model model) {
        model.addAttribute("ownerDto", OwnerDto.builder().build());

        return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/new")
    public String processCreationForm(@Valid @ModelAttribute("ownerDto") OwnerDto ownerDto, BindingResult result) {
        if (result.hasErrors())
            return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
        else {
            Owner savedOwner = ownerService.save(ownerMapper.ownerDtoToOwner(ownerDto));

            return "redirect:/owners/" + savedOwner.getId();
        }
    }

    @GetMapping("/{ownerId}/edit")
    public String initUpdateFrom(@PathVariable("ownerId") Long id, Model model) {
        return findOwner(id, model, VIEWS_OWNER_CREATE_OR_UPDATE_FORM);
    }

    private String findOwner(@PathVariable("ownerId") Long id, Model model, String viewsOwnerCreateOrUpdateForm) {
        Owner owner = ownerService.findById(id);
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
        model.addAttribute("ownerDto", ownerDto);

        return viewsOwnerCreateOrUpdateForm;
    }

    @PostMapping("/{ownerId}/edit")
    public String processUpdateForm(@Valid @ModelAttribute("ownerDto") OwnerDto ownerDto, BindingResult result,
                                    @PathVariable("ownerId") Long id) {
        if (result.hasErrors())
            return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
        else {
            ownerDto.setId(id);
            Owner owner = ownerMapper.ownerDtoToOwner(ownerDto);
            ownerService.save(owner);

            return "redirect:/owners/" + ownerDto.getId();
        }
    }
}
