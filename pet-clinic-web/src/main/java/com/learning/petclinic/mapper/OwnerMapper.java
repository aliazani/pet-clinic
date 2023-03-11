package com.learning.petclinic.mapper;

import com.learning.petclinic.dto.OwnerDto;
import com.learning.petclinic.model.Owner;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(uses = {PetMapper.class})
public interface OwnerMapper {
    @Mapping(target = "pets", ignore = true)
    Owner ownerDtoToOwner(OwnerDto ownerDto);

    @Mapping(target = "pets", ignore = true)
    OwnerDto ownerToOwnerDto(Owner owner);
}
