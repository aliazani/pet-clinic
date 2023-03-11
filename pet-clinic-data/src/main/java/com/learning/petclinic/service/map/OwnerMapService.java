package com.learning.petclinic.service.map;

import com.learning.petclinic.model.Owner;
import com.learning.petclinic.service.OwnerService;
import com.learning.petclinic.service.PetService;
import com.learning.petclinic.service.PetTypeService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Profile("map")
public class OwnerMapService extends AbstractMapService<Owner, Long> implements OwnerService {
    private final PetService petService;
    private final PetTypeService petTypeService;

    public OwnerMapService(PetService petService, PetTypeService petTypeService) {
        this.petService = petService;
        this.petTypeService = petTypeService;
    }

    @Override
    public Set<Owner> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public void delete(Owner item) {
        super.delete(item);
    }

    @Override
    public Owner save(Owner item) {
        if (item.getPets() != null) {
            item.getPets().forEach(pet -> {
                if (pet.getId() == null)
                    pet.setId(petService.save(pet).getId());
                if (pet.getPetType() != null && pet.getPetType().getId() == null)
                    pet.setPetType(petTypeService.save(pet.getPetType()));
            });
            return super.save(item);
        }

        return null;
    }

    @Override
    public Owner findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Owner findByLastName(String lastName) {
        return null;
    }
}
