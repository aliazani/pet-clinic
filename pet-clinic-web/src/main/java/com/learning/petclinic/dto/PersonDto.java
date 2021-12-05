package com.learning.petclinic.dto;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonDto extends BaseDto {
    public PersonDto(Long id, String firstName, String lastName) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    private String firstName;
    private String lastName;
}
