package com.learning.petclinic.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NamedDto extends BaseDto {
    public NamedDto(Long id, String name) {
        super(id);
        this.name = name;
    }

    private String name;

    @Override
    public String toString() {
        return name;
    }
}