package com.learning.petclinic.bootstrap;

import com.learning.petclinic.model.*;
import com.learning.petclinic.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Slf4j
@Profile("map")
public class MapDataLoader implements CommandLineRunner {
    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;
    private final PetService petService;
    private final SpecialityService specialityService;
    private final VisitService visitService;

    public MapDataLoader(OwnerService ownerService, VetService vetService,
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
        PetType dog = PetType.builder()
                .name("Dog")
                .build();
        petTypeService.save(dog);

        PetType cat = PetType.builder()
                .name("Cat")
                .build();
        petTypeService.save(cat);

        Owner owner1 = new Owner();
        owner1.setFirstName("John");
        owner1.setLastName("Steven");
        owner1.setAddress("123 Jay street");
        owner1.setCity("Miami");
        owner1.setTelephone("123456");

        Pet owner1Pet = new Pet();
        owner1Pet.setName("Georgy");
        owner1Pet.setOwner(owner1);
        owner1Pet.setPetType(dog);
        owner1Pet.setBirthDate(LocalDate.now());

        Visit owner1PetVisit1 = new Visit();
        owner1PetVisit1.setDate(LocalDate.now());
        owner1PetVisit1.setDescription("John's dog first visit.");
        owner1PetVisit1.setPet(owner1Pet);

        owner1.getPets().add(owner1Pet);
        owner1Pet.getVisits().add(owner1PetVisit1);

        ownerService.save(owner1);
        petService.save(owner1Pet);
        visitService.save(owner1PetVisit1);

        Owner owner2 = new Owner();
        owner2.setFirstName("Markus");
        owner2.setLastName("Sophia");
        owner2.setAddress("123 Roy street");
        owner2.setCity("Florida");
        owner2.setTelephone("98765");

        Pet owner2Pet = new Pet();
        owner2Pet.setName("Fiona");
        owner2Pet.setPetType(cat);
        owner2Pet.setOwner(owner2);
        owner2Pet.setBirthDate(LocalDate.now());

        Visit owner2PetVisit1 = new Visit();
        owner2PetVisit1.setDate(LocalDate.now());
        owner2PetVisit1.setDescription("first visit to check if the cat is healthy or not.");
        owner2PetVisit1.setPet(owner2Pet);

        Visit owner2PetVisit2 = new Visit();
        owner2PetVisit2.setDate(LocalDate.now());
        owner2PetVisit2.setDescription("second visit to the broken legs.");
        owner2PetVisit2.setPet(owner2Pet);

        owner2Pet.getVisits().add(owner2PetVisit1);
        owner2Pet.getVisits().add(owner2PetVisit2);
        owner2.getPets().add(owner2Pet);

        ownerService.save(owner2);
        petService.save(owner2Pet);
        visitService.save(owner2PetVisit1);
        visitService.save(owner2PetVisit2);

        Owner owner3 = new Owner();
        owner3.setFirstName("Jeff");
        owner3.setLastName("Sophia");
        owner3.setAddress("Love street");
        owner3.setCity("Chicago");
        owner3.setTelephone("828312");

        Pet owner3Pet = new Pet();
        owner3Pet.setName("Jeffy");
        owner3Pet.setPetType(dog);
        owner3Pet.setOwner(owner3);
        owner3Pet.setBirthDate(LocalDate.now());

        Visit owner3PetVisit1 = new Visit();
        owner3PetVisit1.setDate(LocalDate.now());
        owner3PetVisit1.setDescription("first visit to check if the dog is healthy or not.");
        owner3PetVisit1.setPet(owner3Pet);

        owner3.getPets().add(owner3Pet);
        owner3Pet.getVisits().add(owner3PetVisit1);

        ownerService.save(owner3);
        petService.save(owner3Pet);
        visitService.save(owner3PetVisit1);

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
