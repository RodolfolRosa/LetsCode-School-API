package com.study.quarkus.service;

//import com.study.quarkus.dto.AlunoResponse;
import com.study.quarkus.dto.ProfessorRequest;
import com.study.quarkus.dto.ProfessorResponse;
import com.study.quarkus.exception.NotAllowedNameException;
//import com.study.quarkus.mapper.AlunoMapper;
import com.study.quarkus.mapper.ProfessorMapper;
import com.study.quarkus.model.Professor;
import com.study.quarkus.repository.ProfessorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@ApplicationScoped
@Slf4j
@RequiredArgsConstructor
public class ProfessorService {

    private final ProfessorMapper mapper;
    //private final AlunoMapper mapperAluno;
    private final ProfessorRepository repository;

    public List<ProfessorResponse> retrieveAll() {
        log.info("Listando professores");
        final List<Professor> listOfProfessors = repository.listAll();
        return  mapper.toResponse(listOfProfessors);
    }

    public ProfessorResponse getById(int id) {
        log.info("Buscando professor id-{}", id);

        Professor professor = repository.findById(id);
        return mapper.toResponse(professor);
    }

    @Transactional
    public ProfessorResponse save(@Valid ProfessorRequest professorRequest) {

        Objects.requireNonNull(professorRequest, "request must not be null");

        log.info("Salvando professor - {}", professorRequest);


        if (professorRequest.getNome().equals("AAA")) {
            throw new NotAllowedNameException("The name AAA is not allowed");
        }

        Professor entity =
                Professor.builder()
                .nome(professorRequest.getNome())
                .build();

        repository.persistAndFlush(entity);

        return mapper.toResponse(entity);
    }

    @Transactional
    public ProfessorResponse update(int id, @Valid ProfessorRequest professorRequest) {

        Objects.requireNonNull(professorRequest, "request must not be null");

        log.info("Atualizando professor id - {}, data - {}", id, professorRequest);

        Optional<Professor> professor = repository.findByIdOptional(id);

        professor.orElseThrow(() -> new EntityNotFoundException("Professor não encontrado."));

        var entity = professor.get();
        entity.setNome(professorRequest.getNome());
        return mapper.toResponse(entity);
    }

    @Transactional
    public void delete(int id) {
        log.info("Deletando professor id - {}", id);
        Optional<Professor> professor = repository.findByIdOptional(id);
        professor.ifPresent(repository::delete);
    }

    /*
    public List<AlunoResponse> tutorados(int id) {
        var professor = repository.findById(id);
        var alunos = professor.getAlunos();
        return alunos
            .stream()
            .map(aluno -> mapperAluno.toResponse(aluno) )
            .toList();
        //return professor.getAlunos();
        //var tutorados = repository.getA

    }*/
}
