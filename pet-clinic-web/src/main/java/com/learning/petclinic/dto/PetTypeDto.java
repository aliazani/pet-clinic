package com.learning.petclinic.dto;

import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PetTypeDto extends NamedDto {
    @Builder
    public PetTypeDto(Long id, String name) {
        super(id, name);
    }
}
