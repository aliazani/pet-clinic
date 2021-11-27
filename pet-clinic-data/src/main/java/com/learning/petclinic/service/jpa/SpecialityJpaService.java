package com.learning.petclinic.service.jpa;

import com.learning.petclinic.model.Speciality;
import com.learning.petclinic.repository.SpecialityRepository;
import com.learning.petclinic.service.SpecialityService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Profile("jpa")
public class SpecialityJpaService implements SpecialityService {
    private final SpecialityRepository specialityRepository;

    public SpecialityJpaService(SpecialityRepository specialityRepository) {
        this.specialityRepository = specialityRepository;
    }

    @Override
    public Set<Speciality> findAll() {
        return (Set<Speciality>) specialityRepository.findAll();
    }

    @Override
    public Speciality findById(Long id) {
        return specialityRepository.findById(id).orElse(null);
    }

    @Override
    public Speciality save(Speciality item) {
        return specialityRepository.save(item);
    }

    @Override
    public void delete(Speciality item) {
        specialityRepository.delete(item);
    }

    @Override
    public void deleteById(Long id) {
        specialityRepository.deleteById(id);
    }
}
