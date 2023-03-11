package com.learning.petclinic.model;

import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "pet_types")
@NoArgsConstructor
public class PetType extends NamedEntity {
    @Builder
    public PetType(Long id, String name) {
        super(id, name);
    }
}
