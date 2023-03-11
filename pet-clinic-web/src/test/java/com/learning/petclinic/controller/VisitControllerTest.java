package com.learning.petclinic.controller;

import com.learning.petclinic.mapper.PetMapper;
import com.learning.petclinic.mapper.VisitMapper;
import com.learning.petclinic.service.PetService;
import com.learning.petclinic.service.VisitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class VisitControllerTest {
    @Mock
    PetService petService;

    @Mock
    VisitService visitService;

    @Mock
    PetMapper petMapper;

    @Mock
    VisitMapper visitMapper;

    @InjectMocks
    VisitController visitController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockMvcBuilders.standaloneSetup(visitController).build();
    }

    @Test
    void initNewVisitForm() {
    }

    @Test
    void processNewVisitForm() {
    }
}