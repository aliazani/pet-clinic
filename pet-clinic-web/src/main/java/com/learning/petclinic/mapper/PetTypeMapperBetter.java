package com.learning.petclinic.mapper;

import com.learning.petclinic.dto.PetTypeDto;
import com.learning.petclinic.model.PetType;

import java.util.Set;

public interface PetTypeMapperBetter {
    PetType toEntity(PetTypeDto petTypeDto);

    PetTypeDto toDTO(PetType petType);

    Set<PetType> toEntitySet(Set<PetTypeDto> petTypeDtos);

    Set<PetTypeDto> toDTOSet(Set<PetType> petTypes);
}
