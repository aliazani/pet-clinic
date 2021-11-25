package com.learning.petclinic.service.map;

import com.learning.petclinic.model.Vet;
import com.learning.petclinic.service.SpecialityService;
import com.learning.petclinic.service.VetService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
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
        if (item.getSpecialities() != null) {
            item.getSpecialities().forEach(speciality -> {
                if (speciality.getId() == null)
                    speciality.setId(specialityService.save(speciality).getId());
            });
            return super.save(item);
        }
        return null;
    }

    @Override
    public Vet findById(Long id) {
        return super.findById(id);
    }
}
