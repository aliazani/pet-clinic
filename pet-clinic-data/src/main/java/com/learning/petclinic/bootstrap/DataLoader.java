package com.learning.petclinic.bootstrap;

import com.learning.petclinic.model.Owner;
import com.learning.petclinic.model.PetType;
import com.learning.petclinic.model.Vet;
import com.learning.petclinic.service.OwnerService;
import com.learning.petclinic.service.PetTypeService;
import com.learning.petclinic.service.VetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DataLoader implements CommandLineRunner {
    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;

    public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
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
        ownerService.save(owner1);

        Owner owner2 = new Owner();
        owner2.setFirstName("Markus");
        owner2.setLastName("Sophia");
        ownerService.save(owner2);

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
