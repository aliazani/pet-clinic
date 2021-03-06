package com.learning.petclinic.service.map;

import com.learning.petclinic.model.Vet;
import com.learning.petclinic.service.SpecialityService;
import com.learning.petclinic.service.VetService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Profile("map")
public class VetMapService extends AbstractMapService<Vet, Long> implements VetService {
    private final SpecialityService specialityService;

    public VetMapService(SpecialityService specialityService) {
        this.specialityService = specialityService;
    }

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
        if (map.get(item.getId()) != null) {
            var existentItem = map.get(item.getId());
            existentItem.setFirstName(item.getFirstName());
            existentItem.setLastName(item.getLastName());
            item.getSpecialities().forEach(existentItem.getSpecialities()::add);
        }
        return super.save(item);
    }

    @Override
    public Vet findById(Long id) {
        return super.findById(id);
    }
}
