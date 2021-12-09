package com.learning.petclinic.formatter;

import com.learning.petclinic.dto.PetTypeDto;
import com.learning.petclinic.mapper.PetTypeMapper;
import com.learning.petclinic.service.PetTypeService;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@Component
public class PetTypeFormatter implements Formatter<PetTypeDto> {
    private final PetTypeService petTypeService;
    private final PetTypeMapper petTypeMapper;

    public PetTypeFormatter(PetTypeService petTypeService, PetTypeMapper petTypeMapper) {
        this.petTypeService = petTypeService;
        this.petTypeMapper = petTypeMapper;
    }

    @Override
    public PetTypeDto parse(String text, Locale locale) throws ParseException {
        Set<PetTypeDto> petTypeDtos = new HashSet<>();
        petTypeService.findAll().forEach(petType -> petTypeDtos.add(petTypeMapper.petTypeToPetTypeDto(petType)));

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
