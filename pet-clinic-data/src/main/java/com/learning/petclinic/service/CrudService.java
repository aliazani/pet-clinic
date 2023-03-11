package com.learning.petclinic.service;

import java.util.Set;

public interface CrudService<T, ID> {
    Set<T> findAll();

    T findById(ID id);

    T save(T item);

    void delete(T item);

    void deleteById(ID id);
}
