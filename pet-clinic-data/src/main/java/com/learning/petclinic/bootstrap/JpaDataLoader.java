package com.learning.petclinic.bootstrap;

import com.learning.petclinic.model.*;
import com.learning.petclinic.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Set;

@Component
@Slf4j
@Profile("jpa")
public class JpaDataLoader implements CommandLineRunner {
    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;
    private final PetService petService;
    private final SpecialityService specialityService;
    private final VisitService visitService;

    public JpaDataLoader(OwnerService ownerService, VetService vetService,
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
        // PetTypes
        PetType dog = PetType.builder()
                .name("Dog")
                .build();
        petTypeService.save(dog);

        PetType cat = PetType.builder()
                .name("Cat")
                .build();
        petTypeService.save(cat);

        Owner owner1 = Owner.builder()
                .firstName("John")
                .lastName("Steven")
                .address("123 Jay street")
                .city("Miami")
                .telephone("123456")
                .build();
        ownerService.save(owner1);

        Pet owner1Pet = Pet.builder()
                .name("Georgy")
                .owner(owner1)
                .petType(dog)
                .birthDate(LocalDate.now())
                .build();
        petService.save(owner1Pet);

        Visit owner1PetVisit1 = Visit.builder()
                .date(LocalDate.now())
                .description("John's dog first visit.")
                .pet(owner1Pet)
                .build();
        visitService.save(owner1PetVisit1);

        Owner owner2 = Owner.builder()
                .firstName("Markus")
                .lastName("Sophia")
                .address("123 Roy street")
                .city("Florida")
                .telephone("98765")
                .build();
        ownerService.save(owner2);

        Pet owner2Pet = Pet.builder()
                .name("Fiona")
                .petType(cat)
                .owner(owner2)
                .birthDate(LocalDate.now())
                .build();
        petService.save(owner2Pet);

        Visit owner2PetVisit1 = Visit.builder()
                .date(LocalDate.now())
                .description("first visit to check if the cat is healthy or not.")
                .pet(owner2Pet)
                .build();
        visitService.save(owner2PetVisit1);

        Visit owner2PetVisit2 = Visit.builder()
                .date(LocalDate.now())
                .description("second visit to the broken legs.")
                .pet(owner2Pet)
                .build();
        visitService.save(owner2PetVisit2);

        Owner owner3 = Owner.builder()
                .firstName("Jeff")
                .lastName("Sophia")
                .address("Love street")
                .city("Chicago")
                .telephone("828312")
                .build();
        ownerService.save(owner3);

        Pet owner3Pet = Pet.builder()
                .name("Jeffy")
                .petType(dog)
                .owner(owner3)
                .birthDate(LocalDate.now())
                .build();
        petService.save(owner3Pet);

        Visit owner3PetVisit1 = Visit.builder()
                .date(LocalDate.now())
                .description("first visit to check if the dog is healthy or not.")
                .pet(owner3Pet)
                .build();
        visitService.save(owner3PetVisit1);

        Speciality surgery = Speciality.builder()
                .name("Surgery")
                .build();
        specialityService.save(surgery);

        Speciality radiology = Speciality.builder()
                .name("Radiology")
                .build();
        specialityService.save(radiology);

        Speciality dentistry = Speciality.builder()
                .name("Dentistry")
                .build();
        specialityService.save(dentistry);

        Vet vet1 = Vet.builder()
                .firstName("Robert")
                .lastName("Jobs")
                .build();

        vet1.setSpecialities(Set.of(radiology, dentistry));
        vetService.save(vet1);

        Vet vet2 = Vet.builder()
                .firstName("Mary")
                .lastName("Curious")
                .build();
        vet1.setSpecialities(Set.of(surgery, radiology));
        vetService.save(vet2);

        log.info("Data loaded successfully ...");
    }
}
