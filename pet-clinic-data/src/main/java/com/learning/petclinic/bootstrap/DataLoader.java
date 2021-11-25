package com.learning.petclinic.bootstrap;

import com.learning.petclinic.model.Owner;
import com.learning.petclinic.model.Pet;
import com.learning.petclinic.model.PetType;
import com.learning.petclinic.model.Vet;
import com.learning.petclinic.service.OwnerService;
import com.learning.petclinic.service.PetService;
import com.learning.petclinic.service.PetTypeService;
import com.learning.petclinic.service.VetService;
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

    public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService, PetService petService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
        this.petService = petService;
    }

    @Override
    public void run(String... args) throws Exception {
        PetType dog = new PetType();
        dog.setName("Dog");
        PetType savedDog = petTypeService.save(dog);

        PetType cat = new PetType();
        dog.setName("Cat");
        PetType savedCat = petTypeService.save(cat);

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
        owner2.getPets().add(markusCat);

        ownerService.save(owner2);
        petService.save(markusCat);

        Vet vet1 = new Vet();
        vet1.setFirstName("Robert");
        vet1.setLastName("Jobs");
        vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setFirstName("Mary");
        vet2.setLastName("Curious");
        vetService.save(vet2);

        log.info("Data loaded successfully ...");
    }
}
