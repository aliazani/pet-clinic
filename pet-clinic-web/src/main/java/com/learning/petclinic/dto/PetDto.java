package com.learning.petclinic.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
public class PetDto extends NamedDto {
    @Builder
    public PetDto(Long id, String name, LocalDate birthDate, PetTypeDto petType, OwnerDto owner, Set<VisitDto> visits) {
        super(id, name);
        this.birthDate = birthDate;
        this.petType = petType;
        this.owner = owner;
        if (visits == null)
            visits = new HashSet<>();
        this.visits = visits;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
    private PetTypeDto petType;
    private OwnerDto owner;
    private Set<VisitDto> visits = new HashSet<>();
}
