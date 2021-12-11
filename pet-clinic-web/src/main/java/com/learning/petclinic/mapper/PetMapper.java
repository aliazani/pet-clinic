package com.learning.petclinic.mapper;

import com.learning.petclinic.dto.PetDto;
import com.learning.petclinic.model.Pet;
import org.mapstruct.*;

import java.util.Set;

@Mapper(componentModel = "spring", uses = {PetTypeMapper.class, VisitMapper.class})
public interface PetMapper {
    // Single - with visitDTO(no link to petDTO) - with OwnerDTO(no link to petDTO)
    @Named("NoPet")
    @Mapping(target = "visits", qualifiedByName = "NoPet")
    @Mapping(target = "owner.pets", ignore = true)
    PetDto toDTO(Pet pet);

    // Single - without visitDTO - with OwnerDTO(no link to petDTO)
    @Named("NoVisit")
    @Mapping(target = "visits", ignore = true)
    @Mapping(target = "owner.pets", ignore = true)
    PetDto toDTONoVisit(Pet pet);

    // Single - with visitDTO(no link to petDTO) - without OwnerDTO
    @Named("NoOwner")
    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "visits", qualifiedByName = "NoPet")
    PetDto toDTONoOwner(Pet pet);

    // Set - with visitDTO(no link to petDTO) - with OwnerDTO (no link to petDTO)
    @Named("NoPet")
    @IterableMapping(qualifiedByName = "NoPet")
    Set<PetDto> toDTOSet(Set<Pet> pets);

    // Set - without visitDTO - with OwnerDTO (no link to petDTO)
    @Named("NoVisit")
    @IterableMapping(qualifiedByName = "NoVisit")
    Set<PetDto> toDTOSetNoVisit(Set<Pet> pets);

    // Single - with visitDTO(no link to petDTO) - withoutOwner DTO
    @Named("NoOwner")
    @IterableMapping(qualifiedByName = "NoOwner")
    Set<PetDto> toDTOSetNoOwner(Set<Pet> pets);

    // Single - with visit(no link to pet) - with Owner(no link to pet)
    @Named("NoPet")
    @Mapping(target = "visits", qualifiedByName = "NoPet")
    @Mapping(target = "owner.pets", ignore = true)
    Pet toEntity(PetDto petDto);

    // Single - without visit - with Owner(no link to pet)
    @Named("NoVisit")
    @Mapping(target = "visits", ignore = true)
    @Mapping(target = "owner.pets", ignore = true)
    Pet toEntityNoVisit(PetDto petDto);

    // Single - with visit(no link to pet) - without Owner
    @Named("NoOwner")
    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "visits", qualifiedByName = "NoPet")
    Pet toEntityNoOwner(PetDto petDto);

    // Set - with visit(no link to pet) - with Owner(no link to pet)
    @Named("NoPet")
    @IterableMapping(qualifiedByName = "NoPet")
    Set<Pet> toEntitySet(Set<PetDto> petDtos);

    // Set - without visit - with Owner(no link to pet)
    @Named("NoVisit")
    @IterableMapping(qualifiedByName = "NoVisit")
    Set<Pet> toEntitySetNoVisit(Set<PetDto> petDtos);

    // Set - with visit(no link to pet) - without Owner
    @Named("NoOwner")
    @IterableMapping(qualifiedByName = "NoOwner")
    Set<Pet> toEntitySetNoOwner(Set<PetDto> petDtos);
}
