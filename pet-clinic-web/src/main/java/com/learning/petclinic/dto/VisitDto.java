package com.learning.petclinic.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VisitDto extends BaseDto {
    @Builder
    public VisitDto(Long id, LocalDate date, String description, PetDto pet) {
        super(id);
        this.date = date;
        this.description = description;
        this.pet = pet;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @NotEmpty
    private String description;
    private PetDto pet;
}
