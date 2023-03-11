package com.learning.petclinic.controller;

import com.learning.petclinic.service.VetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/vets")
public class VetController {
    private final VetService vetService;

    public VetController(VetService vetService) {
        this.vetService = vetService;
    }

    @GetMapping("")
    public String listVets(Model model) {
        model.addAttribute("listVets", vetService.findAll());
        vetService.findAll().forEach(vet -> vet.getSpecialities());
        return "vets/index";
    }
}
