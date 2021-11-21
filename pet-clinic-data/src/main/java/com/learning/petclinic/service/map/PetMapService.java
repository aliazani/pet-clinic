package com.learning.petclinic.service.map;

import com.learning.petclinic.model.Pet;
import com.learning.petclinic.service.PetService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class PetMapService extends AbstractMapService<Pet, Long> implements PetService {
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
        return super.save(item.getId(), item);
    }

    @Override
    public Pet findById(Long id) {
        return super.findById(id);
    }
}
