package com.learning.petclinic.service.map;

import com.learning.petclinic.model.Pet;
import com.learning.petclinic.service.CrudService;

import java.util.Set;

public class PetMapService extends AbstractMapService<Pet, Long> implements CrudService<Pet, Long> {
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
