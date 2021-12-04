package com.learning.petclinic.mapper;

import com.learning.petclinic.dto.VisitDto;
import com.learning.petclinic.model.Visit;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface VisitMapper {
    Visit visitDtoToVisit(VisitDto visitDto);

    VisitDto visitToVisitDto(Visit visit);
}
