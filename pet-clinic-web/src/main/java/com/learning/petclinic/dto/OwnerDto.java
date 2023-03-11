package com.learning.petclinic.dto;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OwnerDto extends PersonDto {
    @Builder
    public OwnerDto(String firstName, String lastName, String address,
                    String city, String telephone, Set<PetDto> pets) {
        super(firstName, lastName);
        this.address = address;
        this.city = city;
        this.telephone = telephone;
        this.pets = pets;
    }

    private String address;
    private String city;
    private String telephone;
    private Set<PetDto> pets = new HashSet<>();
}