package com.study.quarkus.mapper;

import com.study.quarkus.dto.DisciplinaResponse;
import com.study.quarkus.dto.TitularResponse;
import com.study.quarkus.model.Disciplina;
import com.study.quarkus.model.Professor;

import javax.enterprise.context.ApplicationScoped;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ApplicationScoped
public class DisciplinaMapper {

    public List<DisciplinaResponse> toResponse(List<Disciplina> listOfEntities) {

        if (Objects.isNull(listOfEntities)) return new ArrayList<>();

        return listOfEntities.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());

    }

    public DisciplinaResponse toResponse(Disciplina entity) {

        if (Objects.isNull(entity)) return null;

        var formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss");

        var response = DisciplinaResponse.builder()
                    .id(entity.getId())
                    .nome(entity.getNome())
                    .dateTime(formatter.format(entity.getDateTime()))
                    .build();

        if (Objects.nonNull(entity.getTitular())) {
            response.setTitular(entity.getTitular().getNome());
        }

        return response;
    }

    public TitularResponse toResponse(Professor entity) {

        if (Objects.isNull(entity)) return null;

        var formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss");

        return TitularResponse.builder()
                .titular(entity.getNome())
                .atualizacao(formatter.format(LocalDateTime.now()))
                .build();

    }
}