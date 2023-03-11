package com.learning.petclinic.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;


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

    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
}
