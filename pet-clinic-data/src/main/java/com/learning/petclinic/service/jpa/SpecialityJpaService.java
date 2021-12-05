package com.learning.petclinic.service.jpa;

import com.learning.petclinic.model.Speciality;
import com.learning.petclinic.repository.SpecialityRepository;
import com.learning.petclinic.service.SpecialityService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;

@Service
@Profile("jpa")
public class SpecialityJpaService implements SpecialityService {
    private final SpecialityRepository specialityRepository;

    public SpecialityJpaService(SpecialityRepository specialityRepository) {
        this.specialityRepository = specialityRepository;
    }

    @Transactional
    @Override
    public Set<Speciality> findAll() {
        return (Set<Speciality>) specialityRepository.findAll();
    }

    @Transactional
    @Override
    public Speciality findById(Long id) {
        return specialityRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public Speciality save(Speciality item) {
        return specialityRepository.save(item);
    }

    @Transactional
    @Override
    public void delete(Speciality item) {
        specialityRepository.delete(item);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        specialityRepository.deleteById(id);
    }
}
