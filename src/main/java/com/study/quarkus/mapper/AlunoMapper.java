package com.study.quarkus.mapper;

import com.study.quarkus.dto.AlunoResponse;
import com.study.quarkus.dto.TutorResponse;
import com.study.quarkus.model.Aluno;
import com.study.quarkus.model.Professor;

import javax.enterprise.context.ApplicationScoped;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ApplicationScoped
public class AlunoMapper {

    public List<AlunoResponse> toResponse(List<Aluno> listOfAlunos) {

        if (Objects.isNull(listOfAlunos)) return new ArrayList<>();

        return listOfAlunos.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());

    }

    public AlunoResponse toResponse(Aluno entity) {

        Objects.requireNonNull(entity, "Entity must be not null");

        var formatter = DateTimeFormatter.ofPattern("dd-MM-YYYY hh:mm:ss");

        var response = AlunoResponse.builder()
                    .id(entity.getId())
                    .nome(entity.getNome())
                    .dateTime(formatter.format(entity.getDateTime()))
                    .build();

        if (Objects.nonNull(entity.getTutor())) {
            response.setTutor(entity.getTutor().getNome());
        }

        return response;
    }

    public TutorResponse toResponse(Professor entity) {

        if (Objects.isNull(entity)) return null;

        var formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss");

        return TutorResponse.builder()
                .tutor(entity.getNome())
                .atualizacao(formatter.format(LocalDateTime.now()))
                .build();

    }
    
}
