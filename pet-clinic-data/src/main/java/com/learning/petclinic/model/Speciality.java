package com.learning.petclinic.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "specialties")
@NoArgsConstructor
public class Speciality extends NamedEntity {
    @Builder
    public Speciality(Long id, String name) {
        super(id, name);
    }
}
