package com.learning.petclinic.mapper;

import com.learning.petclinic.dto.OwnerDto;
import com.learning.petclinic.model.Owner;
import org.mapstruct.*;

import java.util.Set;

@Mapper(componentModel = "spring", uses = {PetMapper.class})
public interface OwnerMapper {
    // Single - with petDTO(no link to owner)
    @Named("NoOwner")
    @Mapping(target = "pets", qualifiedByName = "NoOwner")
    OwnerDto toDTO(Owner owner);

    // Single - without petDTO
    @Named("NoPet")
    @Mapping(target = "pets", ignore = true)
    OwnerDto toDTONoPet(Owner owner);

    // Set - with petDTO(no link to owner)
    @Named("NoOwner")
    @IterableMapping(qualifiedByName = "NoOwner")
    Set<OwnerDto> toDTOSet(Set<Owner> owner);

    // Set - without petDTO
    @Named("NoPet")
    @IterableMapping(qualifiedByName = "NoPet")
    Set<OwnerDto> toDTOSetNoPet(Set<Owner> owners);

    // Single - with pet(no link to owner)
    @Named("NoOwner")
    @Mapping(target = "pets", qualifiedByName = "NoOwner")
    Owner toEntity(OwnerDto ownerDto);

    // Single - without pet
    @Named("NoPet")
    @Mapping(target = "pets", ignore = true)
    Owner toEntityNoPet(OwnerDto ownerDto);

    // Set - with pet(no link to owner)
    @Named("NoOwner")
    @IterableMapping(qualifiedByName = "NoOwner")
    Set<Owner> toEntitySet(Set<OwnerDto> ownerDtos);

    // Set - without pet
    @Named("NoPet")
    @IterableMapping(qualifiedByName = "NoPet")
    Set<Owner> toEntitySetNoPet(Set<OwnerDto> ownerDtos);
}
