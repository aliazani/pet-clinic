package com.learning.petclinic.repository;

import com.learning.petclinic.model.Owner;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;

public interface OwnerRepository extends CrudRepository<Owner, Long> {
    Optional<Owner> findByLastName(String lastName);

    Set<Owner> findAllByLastNameContainingIgnoreCase(String lastName);
}
