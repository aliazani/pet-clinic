package com.learning.petclinic.mapper;

import com.learning.petclinic.dto.PetTypeDto;
import com.learning.petclinic.model.PetType;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface PetTypeMapper {
    PetType petTypeDtoToPetType(PetTypeDto petTypeDto);

    PetTypeDto petTypeToPetTypeDto(PetType petType);
}
