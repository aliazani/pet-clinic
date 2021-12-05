package com.learning.petclinic.service.jpa;

import com.learning.petclinic.model.Vet;
import com.learning.petclinic.repository.VetRepository;
import com.learning.petclinic.service.VetService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
@Profile("jpa")
public class VetJpaService implements VetService {
    private final VetRepository vetRepository;

    public VetJpaService(VetRepository vetRepository) {
        this.vetRepository = vetRepository;
    }

    @Transactional
    @Override
    public Set<Vet> findAll() {
        Set<Vet> vets = new HashSet<>();
        vetRepository.findAll().forEach(vets::add);
        return vets;
    }

    @Transactional
    @Override
    public Vet findById(Long id) {
        return vetRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public Vet save(Vet item) {
        return vetRepository.save(item);
    }

    @Transactional
    @Override
    public void delete(Vet item) {
        vetRepository.delete(item);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        vetRepository.deleteById(id);
    }
}
