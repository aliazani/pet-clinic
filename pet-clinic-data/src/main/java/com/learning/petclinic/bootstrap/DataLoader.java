package com.learning.petclinic.bootstrap;

import com.learning.petclinic.model.*;
import com.learning.petclinic.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Slf4j
public class DataLoader implements CommandLineRunner {
    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;
    private final PetService petService;
    private final SpecialityService specialityService;
    private final VisitService visitService;

    public DataLoader(OwnerService ownerService, VetService vetService,
                      PetTypeService petTypeService, PetService petService,
                      SpecialityService specialityService, VisitService visitService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
        this.petService = petService;
        this.specialityService = specialityService;
        this.visitService = visitService;
    }

    @Override
    public void run(String... args) throws Exception {
        loadData();
    }

    private void loadData() {
        PetType dog = new PetType();
        dog.setName("Dog");
        petTypeService.save(dog);

        PetType cat = new PetType();
        dog.setName("Cat");
        petTypeService.save(cat);

        Owner owner1 = new Owner();
        owner1.setFirstName("John");
        owner1.setLastName("Steven");
        owner1.setAddress("123 Jay street");
        owner1.setCity("Miami");
        owner1.setTelephone("123456");

        Pet johnDog = new Pet();
        johnDog.setName("Georgy");
        johnDog.setOwner(owner1);
        johnDog.setPetType(dog);
        johnDog.setBirthDate(LocalDate.now());
        owner1.getPets().add(johnDog);

        ownerService.save(owner1);
        petService.save(johnDog);

        Owner owner2 = new Owner();
        owner2.setFirstName("Markus");
        owner2.setLastName("Sophia");
        owner2.setAddress("123 Roy street");
        owner2.setCity("Florida");
        owner2.setTelephone("98765");

        Pet markusCat = new Pet();
        markusCat.setName("Fiona");
        markusCat.setPetType(cat);
        markusCat.setOwner(owner2);
        markusCat.setBirthDate(LocalDate.now());

        Visit visit1 = new Visit();
        visit1.setDate(LocalDate.now());
        visit1.setDescription("first visit to check if the cat is healthy or not.");
        visit1.setPet(markusCat);

        Visit visit2 = new Visit();
        visit2.setDate(LocalDate.now());
        visit2.setDescription("second visit to the broken legs.");
        visit2.setPet(markusCat);

        visitService.save(visit1);
        visitService.save(visit2);

        markusCat.getVisits().add(visit1);
        markusCat.getVisits().add(visit2);
        owner2.getPets().add(markusCat);

        ownerService.save(owner2);
        petService.save(markusCat);


        Speciality surgery = new Speciality();
        surgery.setName("Surgery");

        Speciality radiology = new Speciality();
        radiology.setName("Radiology");

        Speciality dentistry = new Speciality();
        dentistry.setName("Dentistry");

        specialityService.save(surgery);
        specialityService.save(radiology);
        specialityService.save(dentistry);

        Vet vet1 = new Vet();
        vet1.setFirstName("Robert");
        vet1.setLastName("Jobs");
        vet1.getSpecialities().add(radiology);
        vet1.getSpecialities().add(dentistry);
        vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setFirstName("Mary");
        vet2.setLastName("Curious");
        vet2.getSpecialities().add(surgery);
        vet2.getSpecialities().add(radiology);
        vetService.save(vet2);

        log.info("Data loaded successfully ...");
    }
}
