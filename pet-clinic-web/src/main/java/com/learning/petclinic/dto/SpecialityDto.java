package com.learning.petclinic.dto;

import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SpecialityDto extends NamedDto {
    @Builder
    public SpecialityDto(Long id, String name) {
        super(id, name);
    }
}
