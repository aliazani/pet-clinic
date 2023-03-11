package com.learning.petclinic.service.jpa;

import com.learning.petclinic.model.PetType;
import com.learning.petclinic.repository.PetTypeRepository;
import com.learning.petclinic.service.PetTypeService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
@Profile("jpa")
public class PetTypeJpaService implements PetTypeService {
    private final PetTypeRepository petTypeRepository;

    public PetTypeJpaService(PetTypeRepository petTypeRepository) {
        this.petTypeRepository = petTypeRepository;
    }

    @Transactional
    @Override
    public Set<PetType> findAll() {
        Set<PetType> petTypes = new HashSet<>();
        petTypeRepository.findAll().forEach(petTypes::add);
        return petTypes;
    }

    @Transactional
    @Override
    public PetType findById(Long id) {
        return petTypeRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public PetType save(PetType item) {
        return petTypeRepository.save(item);
    }

    @Transactional
    @Override
    public void delete(PetType item) {
        petTypeRepository.delete(item);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        petTypeRepository.deleteById(id);
    }
}
