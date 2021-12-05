package com.learning.petclinic.service.jpa;

import com.learning.petclinic.model.Visit;
import com.learning.petclinic.repository.VisitRepository;
import com.learning.petclinic.service.VisitService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;

@Service
@Profile("jpa")
public class VisitJpaService implements VisitService {
    private final VisitRepository visitRepository;

    public VisitJpaService(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    @Transactional
    @Override
    public Set<Visit> findAll() {
        return (Set<Visit>) visitRepository.findAll();
    }

    @Transactional
    @Override
    public Visit findById(Long id) {
        return visitRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public Visit save(Visit item) {
        return visitRepository.save(item);
    }

    @Transactional
    @Override
    public void delete(Visit item) {
        visitRepository.delete(item);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        visitRepository.deleteById(id);
    }
}
