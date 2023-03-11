package com.learning.petclinic.mapper;

import com.learning.petclinic.dto.VisitDto;
import com.learning.petclinic.model.Visit;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;

@Mapper(componentModel = "spring", uses = PetTypeMapperBetter.class)
public interface VisitMapperBetter {
    // Single - with PetDTO(no link to VisitDTO)
    @Named("NoVisit")
//    @Mapping(target = "pet", qualifiedByName = "NoVisit")
    @Mapping(target = "pet.visits", ignore = true)
    VisitDto toDTO(Visit visit);

    // Single - without PetDTO(no link to VisitDTO)
    @Named("NoPet")
    @Mapping(target = "pet", ignore = true)
    VisitDto toDTONoPet(Visit visit);

    // Set - with PetDTO(no link to VisitDTO)
    @Named("NoVisit")
    @IterableMapping(qualifiedByName = "NoVisit")
    Set<VisitDto> toDtoSet(Set<Visit> visits);

    // Set - without PetDTO
    @Named("NoPet")
    @IterableMapping(qualifiedByName = "NoPet")
    Set<VisitDto> toDtoSetNoPet(Set<Visit> visits);

    // Single - with PetDTO(no link to VisitDTO)
    @Named("NoVisit")
//    @Mapping(target = "pet", qualifiedByName = "NoVisit")
    @Mapping(target = "pet.visits", ignore = true)
    Visit toEntity(VisitDto visitDto);

    // Single - without Pet(no link to VisitDTO)
    @Named("NoPet")
    @Mapping(target = "pet", ignore = true)
    Visit toEntityNoPet(VisitDto visitDto);

    // Set - with PetDTO(no link to VisitDTO)
    @Named("NoVisit")
    @IterableMapping(qualifiedByName = "NoVisit")
    Set<Visit> toEntitySet(Set<VisitDto> visitDtos);

    // Set - without Pet(no link to VisitDTO)
    @Named("NoPet")
    @IterableMapping(qualifiedByName = "NoPet")
    Set<Visit> toEntitySetNoPet(Set<VisitDto> visitDtos);
}
