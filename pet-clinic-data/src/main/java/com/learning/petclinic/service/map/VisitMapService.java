package com.learning.petclinic.service.map;


import com.learning.petclinic.model.Visit;
import com.learning.petclinic.service.VisitService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Profile("map")
public class VisitMapService extends AbstractMapService<Visit, Long> implements VisitService {
    @Override
    public Set<Visit> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public void delete(Visit item) {
        super.delete(item);
    }

    @Override
    public Visit save(Visit item) {
        if (map.get(item.getId()) != null) {
            var existentItem = map.get(item.getId());
            existentItem.setPet(item.getPet());
            existentItem.setDate(item.getDate());
            existentItem.setDescription(item.getDescription());
        }
        return super.save(item);
    }

    @Override
    public Visit findById(Long id) {
        return super.findById(id);
    }
}
