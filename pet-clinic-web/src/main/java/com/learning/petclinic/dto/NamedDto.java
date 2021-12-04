package com.learning.petclinic.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NamedDto extends BaseDto {
    private String name;

    @Override
    public String toString() {
        return name;
    }
}