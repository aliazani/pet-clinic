package com.learning.petclinic.service.jpa;

import com.learning.petclinic.model.Pet;
import com.learning.petclinic.repository.PetRepository;
import com.learning.petclinic.service.PetService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
@Profile("jpa")
public class PetJpaService implements PetService {
    private final PetRepository petRepository;

    public PetJpaService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @Transactional
    @Override
    public Set<Pet> findAll() {
        Set<Pet> pets = new HashSet<>();
        petRepository.findAll().forEach(pets::add);
        return pets;
    }

    @Transactional
    @Override
    public Pet findById(Long id) {
        return petRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public Pet save(Pet item) {
        return petRepository.save(item);
    }

    @Transactional
    @Override
    public void delete(Pet item) {
        petRepository.delete(item);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        petRepository.deleteById(id);
    }
}
