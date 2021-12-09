package com.learning.petclinic.mapper;

import com.learning.petclinic.dto.PetDto;
import com.learning.petclinic.model.Pet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(uses = {PetTypeMapper.class, OwnerMapper.class})
public interface PetMapper {
    @Mapping(target = "visits", ignore = true)
    Pet petDtoToPet(PetDto petDto);

    @Mapping(target = "visits", ignore = true)
    PetDto petToPetDto(Pet pet);
}
