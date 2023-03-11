package com.learning.petclinic.formatter;

import com.learning.petclinic.dto.PetTypeDto;
import com.learning.petclinic.mapper.PetTypeMapper;
import com.learning.petclinic.service.PetTypeService;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;
import java.util.Set;

@Component
public class PetTypeDtoFormatter implements Formatter<PetTypeDto> {
    private final PetTypeService petTypeService;
    private final PetTypeMapper petTypeMapperBetter;

    public PetTypeDtoFormatter(PetTypeService petTypeService, PetTypeMapper petTypeMapperBetter) {
        this.petTypeService = petTypeService;
        this.petTypeMapperBetter = petTypeMapperBetter;
    }

    @Override
    public PetTypeDto parse(String text, Locale locale) throws ParseException {
        Set<PetTypeDto> petTypeDtos = petTypeMapperBetter.toDTOSet(petTypeService.findAll());

        for (var petTypeDto : petTypeDtos)
            if (petTypeDto.getName().equals(text))
                return petTypeDto;

        throw new ParseException("PetType not found: " + text, 0);
    }

    @Override
    public String print(PetTypeDto petTypeDto, Locale locale) {
        return petTypeDto.getName();
    }
}
