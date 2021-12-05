package com.learning.petclinic.service.map;

import com.learning.petclinic.model.BaseEntity;

import java.util.*;

public abstract class AbstractMapService<T extends BaseEntity, I extends Long> {
    protected Map<Long, T> map = new HashMap<>();

    Set<T> findAll() {
        return new HashSet<>(map.values());
    }

    T findById(I id) {
        return map.get(id);
    }

    T save(T item) {
        if (item.getId() == null) {
            item.setId(getNextId());
            map.put(item.getId(), item);
        }
        return item;
    }

    void deleteById(I id) {
        map.remove(id);
    }

    void delete(T item) {
        map.entrySet().removeIf(entry -> entry.getValue().equals(item));
    }

    private Long getNextId() {
        return map.size() + 1L;
    }
}
