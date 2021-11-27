package com.learning.petclinic.service.jpa;

import com.learning.petclinic.model.Visit;
import com.learning.petclinic.repository.VisitRepository;
import com.learning.petclinic.service.VisitService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Profile("jpa")
public class VisitJpaService implements VisitService {
    private final VisitRepository visitRepository;

    public VisitJpaService(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    @Override
    public Set<Visit> findAll() {
        return (Set<Visit>) visitRepository.findAll();
    }

    @Override
    public Visit findById(Long id) {
        return visitRepository.findById(id).orElse(null);
    }

    @Override
    public Visit save(Visit item) {
        return visitRepository.save(item);
    }

    @Override
    public void delete(Visit item) {
        visitRepository.delete(item);
    }

    @Override
    public void deleteById(Long id) {
        visitRepository.deleteById(id);
    }
}
