package com.learning.petclinic.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseDto implements Serializable {
    private Long id;

    public boolean isNew() {
        return this.id == null;
    }
}
