package com.learning.petclinic.service;

import com.learning.petclinic.model.Owner;

import java.util.Set;


public interface OwnerService extends CrudService<Owner, Long> {
    Owner findByLastName(String lastName);

    Set<Owner> findAllByLastNameLikeIgnoreCase(String lastName);
}
