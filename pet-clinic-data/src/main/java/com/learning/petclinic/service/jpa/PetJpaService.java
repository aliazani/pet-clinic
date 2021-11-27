package com.learning.petclinic.service.jpa;

import com.learning.petclinic.model.Pet;
import com.learning.petclinic.repository.PetRepository;
import com.learning.petclinic.service.PetService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Profile("jpa")
public class PetJpaService implements PetService {
    private final PetRepository petRepository;

    public PetJpaService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @Override
    public Set<Pet> findAll() {
        return (Set<Pet>) petRepository.findAll();
    }

    @Override
    public Pet findById(Long id) {
        return petRepository.findById(id).orElse(null);
    }

    @Override
    public Pet save(Pet item) {
        return petRepository.save(item);
    }

    @Override
    public void delete(Pet item) {
        petRepository.delete(item);
    }

    @Override
    public void deleteById(Long id) {
        petRepository.deleteById(id);
    }
}
