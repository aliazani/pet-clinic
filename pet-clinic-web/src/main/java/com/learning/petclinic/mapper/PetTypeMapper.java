package com.learning.petclinic.mapper;

import com.learning.petclinic.dto.PetTypeDto;
import com.learning.petclinic.model.PetType;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface PetTypeMapper {
    PetType toEntity(PetTypeDto petTypeDto);

    PetTypeDto toDTO(PetType petType);

    Set<PetType> toEntitySet(Set<PetTypeDto> petTypeDtos);

    Set<PetTypeDto> toDTOSet(Set<PetType> petTypes);
}
