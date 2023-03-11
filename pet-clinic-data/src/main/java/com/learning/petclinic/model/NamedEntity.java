package com.learning.petclinic.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class NamedEntity extends BaseEntity {
    @Column(name = "name")
    private String name;
}