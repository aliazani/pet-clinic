package com.learning.petclinic.service.map;

import com.learning.petclinic.model.Owner;
import com.learning.petclinic.service.OwnerService;
import com.learning.petclinic.service.PetService;
import com.learning.petclinic.service.PetTypeService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@Profile("map")
public class OwnerMapService extends AbstractMapService<Owner, Long> implements OwnerService {
    private final PetService petService;
    private final PetTypeService petTypeService;

    public OwnerMapService(PetService petService, PetTypeService petTypeService) {
        this.petService = petService;
        this.petTypeService = petTypeService;
    }

    @Override
    public Set<Owner> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public void delete(Owner item) {
        super.delete(item);
    }

    @Override
    public Owner save(Owner item) {
        if (map.get(item.getId()) != null) {
            var existentItem = map.get(item.getId());
            existentItem.setFirstName(item.getFirstName());
            existentItem.setLastName(item.getLastName());
            existentItem.setCity(item.getCity());
            existentItem.setAddress(item.getAddress());
            existentItem.setTelephone(item.getTelephone());
        }
        return super.save(item);
    }

    @Override
    public Owner findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Owner findByLastName(String lastName) {
        return super.findAll()
                .stream()
                .filter(owner -> owner.getLastName().equalsIgnoreCase(lastName))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Set<Owner> findAllByLastNameLikeIgnoreCase(String lastName) {
        if (lastName.equals(""))
            return super.findAll();
        else
            return super.findAll()
                    .stream()
                    .filter(owner -> owner.getLastName().toLowerCase()
                            .contains(lastName))
                    .collect(Collectors.toSet());
    }
}
