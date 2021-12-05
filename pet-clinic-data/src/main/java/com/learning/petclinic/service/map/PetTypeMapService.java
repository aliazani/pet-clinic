package com.learning.petclinic.service.map;

import com.learning.petclinic.model.PetType;
import com.learning.petclinic.service.PetTypeService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Profile("map")
public class PetTypeMapService extends AbstractMapService<PetType, Long> implements PetTypeService {
    @Override
    public Set<PetType> findAll() {
        return super.findAll();
    }

    @Override
    public PetType findById(Long id) {
        return super.findById(id);
    }

    @Override
    public PetType save(PetType item) {
        if (map.get(item.getId()) != null) {
            var existentItem = map.get(item.getId());
            existentItem.setName(item.getName());
        }
        return super.save(item);
    }

    @Override
    public void delete(PetType item) {
        super.delete(item);
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }
}
