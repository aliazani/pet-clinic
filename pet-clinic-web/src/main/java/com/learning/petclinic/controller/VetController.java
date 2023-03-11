package com.learning.petclinic.controller;

import com.learning.petclinic.model.Vet;
import com.learning.petclinic.service.VetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Set;

@Controller
@RequestMapping("/vets")
public class VetController {
    private final VetService vetService;

    public VetController(VetService vetService) {
        this.vetService = vetService;
    }

    @GetMapping({"", "vetList.html", "vetList"})
    public String listVets(Model model) {
        model.addAttribute("vets", vetService.findAll());
        return "vets/vetList";
    }

    @GetMapping("/api")
    @ResponseBody
    public Set<Vet> getVetsJson() {
        return vetService.findAll();
    }
}
