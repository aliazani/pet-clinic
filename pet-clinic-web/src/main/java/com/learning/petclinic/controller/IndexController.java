package com.learning.petclinic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class IndexController {
    @GetMapping("/")
    public String index() {
        return "welcome";
    }

    @GetMapping("/oups")
    public String oups(Model model) {
        return "error";
    }
}
