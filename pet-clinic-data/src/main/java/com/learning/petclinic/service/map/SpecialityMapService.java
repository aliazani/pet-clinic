package com.learning.petclinic.service.map;

import com.learning.petclinic.model.Speciality;
import com.learning.petclinic.service.SpecialityService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Profile("map")
public class SpecialityMapService extends AbstractMapService<Speciality, Long> implements SpecialityService {
    @Override
    public Set<Speciality> findAll() {
        return super.findAll();
    }

    @Override
    public Speciality findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Speciality save(Speciality item) {
        if (map.get(item.getId()) != null) {
            var existentItem = map.get(item.getId());
            existentItem.setName(item.getName());
        }
        return super.save(item);
    }

    @Override
    public void delete(Speciality item) {
        super.delete(item);
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }
}
