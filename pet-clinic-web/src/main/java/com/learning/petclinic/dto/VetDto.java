package com.learning.petclinic.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
public class VetDto extends PersonDto {
    @Builder
    public VetDto(String firstName, String lastName, Set<SpecialityDto> specialities) {
        super(firstName, lastName);
        this.specialities = specialities;
    }

    private Set<SpecialityDto> specialities = new HashSet<>();
}
