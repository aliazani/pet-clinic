package com.learning.petclinic.service.map;

import com.learning.petclinic.model.Pet;
import com.learning.petclinic.service.PetService;
import com.learning.petclinic.service.VisitService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Profile("map")
public class PetMapService extends AbstractMapService<Pet, Long> implements PetService {
    private final VisitService visitService;

    public PetMapService(VisitService visitService) {
        this.visitService = visitService;
    }

    @Override
    public Set<Pet> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public void delete(Pet item) {
        super.delete(item);
    }

    @Override
    public Pet save(Pet item) {
        if (map.get(item.getId()) != null) {
            var existentItem = map.get(item.getId());
            existentItem.setName(item.getName());
            existentItem.setOwner(item.getOwner());
            existentItem.setBirthDate(item.getBirthDate());
            existentItem.setOwner(item.getOwner());
            existentItem.setPetType(item.getPetType());
        }
        return super.save(item);
    }

    @Override
    public Pet findById(Long id) {
        return super.findById(id);
    }
}
