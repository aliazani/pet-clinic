package com.learning.petclinic.dto;

import lombok.*;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OwnerDto extends PersonDto {
    @Builder
    public OwnerDto(Long id, String firstName, String lastName, String address,
                    String city, String telephone, Set<PetDto> pets) {
        super(id, firstName, lastName);
        this.address = address;
        this.city = city;
        this.telephone = telephone;
        if (pets == null)
            pets = new HashSet<>();
        this.pets = pets;
    }

    @NotEmpty
    private String address;

    @NotEmpty
    private String city;

    @NotEmpty
    @Digits(fraction = 0, integer = 10)
    private String telephone;
    private Set<PetDto> pets = new HashSet<>();
}
