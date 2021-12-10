package com.learning.petclinic.controller;

import com.learning.petclinic.dto.OwnerDto;
import com.learning.petclinic.mapper.*;
import com.learning.petclinic.model.Owner;
import com.learning.petclinic.service.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@Controller
@RequestMapping("/owners")
public class OwnerController {
    public static final String VIEWS_OWNER_CREATE_OR_UPDATE_FORM = "owners/createOrUpdateOwnerForm";
    public static final String VIEWS_OWNER_DETAILS = "owners/ownerDetails";
    public static final String VIEWS_OWNER_FIND_OWNERS = "owners/findOwners";
    public static final String VIEWS_OWNER_LIST = "owners/ownersList";
    private final OwnerService ownerService;
    private final OwnerMapper ownerMapper;

    public OwnerController(OwnerService ownerService,
                           OwnerMapper ownerMapper) {
        this.ownerService = ownerService;
        this.ownerMapper = ownerMapper;
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

        Set<Owner> owners = ownerService.findAllByLastNameLikeIgnoreCase(ownerDto.getLastName().strip().toLowerCase());
        Set<OwnerDto> ownerDtos = ownerMapper.toDTOSet(owners);


        if (ownerDtos.isEmpty()) {
            result.rejectValue("lastName", "notFound", "notFound");

            return VIEWS_OWNER_FIND_OWNERS;
        } else if (ownerDtos.size() == 1) {
            ownerDto = ownerDtos.iterator().next();
            ownerDto.setId(ownerDto.getId());

            return "redirect:/owners/" + ownerDto.getId();
        } else {
            model.addAttribute("ownerDtos", ownerDtos);

            return VIEWS_OWNER_LIST;
        }
    }

    @GetMapping("/{ownerId}")
    public String showOwner(@PathVariable("ownerId") Long id, Model model) {
        Owner owner = ownerService.findById(id);
        OwnerDto ownerDto = ownerMapper.toDTO(owner);

        model.addAttribute("ownerDto", ownerDto);

        return VIEWS_OWNER_DETAILS;

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
            Owner savedOwner = ownerService.save(ownerMapper.toEntityNoPet(ownerDto));

            return "redirect:/owners/" + savedOwner.getId();
        }
    }

    @GetMapping("/{ownerId}/edit")
    public String initUpdateFrom(@PathVariable("ownerId") Long id, Model model) {
        Owner owner = ownerService.findById(id);
        OwnerDto ownerDto = ownerMapper.toDTO(owner);
        model.addAttribute("ownerDto", ownerDto);

        return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/{ownerId}/edit")
    public String processUpdateForm(@Valid @ModelAttribute("ownerDto") OwnerDto ownerDto, BindingResult result,
                                    @PathVariable("ownerId") Long id) {
        if (result.hasErrors())
            return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
        else {
            ownerDto.setId(id);
            Owner owner = ownerMapper.toEntity(ownerDto);
            ownerService.save(owner);

            return "redirect:/owners/" + ownerDto.getId();
        }
    }
}
