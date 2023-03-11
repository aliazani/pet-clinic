package com.learning.petclinic.service.map;

import com.learning.petclinic.model.Vet;
import com.learning.petclinic.service.CrudService;

import java.util.Set;

public class VetMapService extends AbstractMapService<Vet, Long> implements CrudService<Vet, Long> {
    @Override
    public Set<Vet> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public void delete(Vet item) {
        super.delete(item);
    }

    @Override
    public Vet save(Vet item) {
        return super.save(item.getId(), item);
    }

    @Override
    public Vet findById(Long id) {
        return super.findById(id);
    }
}
