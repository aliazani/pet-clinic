package com.learning.petclinic.controller;

import com.learning.petclinic.dto.OwnerDto;
import com.learning.petclinic.mapper.OwnerMapper;
import com.learning.petclinic.model.Owner;
import com.learning.petclinic.service.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

@Controller
@RequestMapping("/owners")
public class OwnerController {
    private final OwnerService ownerService;
    private final OwnerMapper ownerMapper;

    public OwnerController(OwnerService ownerService, OwnerMapper ownerMapper) {
        this.ownerService = ownerService;
        this.ownerMapper = ownerMapper;
    }

    @GetMapping("/find")
    public String findOwners(Model model) {
        model.addAttribute("owner", OwnerDto.builder().build());

        return "owners/findOwners";
    }

    @GetMapping()
    public String processFindForm(OwnerDto owner, BindingResult result, Model model) {
        if (owner.getLastName() == null)
            owner.setLastName("");
        Set<Owner> results = ownerService.findAllByLastNameLike(owner.getLastName());

        if (results.isEmpty()) {
            result.rejectValue("lastName", "notFound", "notFound");

            return "owners/findOwners";
        } else if (results.size() == 1) {
            owner = ownerMapper.ownerToOwnerDto(results.iterator().next());
            owner.setId(owner.getId());

            return "redirect:/owners/" + owner.getId();
        } else {
            model.addAttribute("owners", results);

            return "owners/ownersList";
        }
    }

    @GetMapping("/{ownerId}")
    public String showOwner(@PathVariable("ownerId") Long id, Model model) {
        model.addAttribute("owner", ownerService.findById(id));
        return "owners/ownerDetails";
    }
}
