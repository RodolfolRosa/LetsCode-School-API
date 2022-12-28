package com.study.quarkus.service;

import com.study.quarkus.dto.AlunoRequest;
import com.study.quarkus.dto.AlunoResponse;
import com.study.quarkus.dto.TutorResponse;
import com.study.quarkus.exception.NotAllowedNameException;

import com.study.quarkus.mapper.AlunoMapper;
import com.study.quarkus.model.Aluno;
import com.study.quarkus.repository.AlunoRepository;
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
public class AlunoService {

    private final AlunoMapper mapper;
    private final AlunoRepository repository;
    private final ProfessorRepository professorRepository;

    public List<AlunoResponse> retrieveAll() {
        log.info("Listando alunos");
        final List<Aluno> listOfAlunos = repository.listAll();
        return  mapper.toResponse(listOfAlunos);
    }

    @Transactional
    public AlunoResponse save(@Valid AlunoRequest alunoRequest) {
        Objects.requireNonNull(alunoRequest, "request must not be null");

        log.info("Salvando aluno - {}", alunoRequest);

        if (alunoRequest.getNome().equals("AAA")) {
            throw new NotAllowedNameException("The name AAA is not allowed");
        }

        Aluno entity =
                Aluno.builder()
                .nome(alunoRequest.getNome())
                .build();

        repository.persistAndFlush(entity);

        return mapper.toResponse(entity);
    }

    public AlunoResponse getById(int id) {
        log.info("Buscando aluno id-{}", id);

        Aluno aluno = repository.findById(id);
        return mapper.toResponse(aluno);
    }

    @Transactional
    public AlunoResponse update(int id, @Valid AlunoRequest alunoRequest) {

        Objects.requireNonNull(alunoRequest, "request must not be null");

        log.info("Atualizando aluno id - {}, data - {}", id, alunoRequest);

        Optional<Aluno> aluno = repository.findByIdOptional(id);

        aluno.orElseThrow(() -> new EntityNotFoundException("Aluno não encontrado."));

        var entity = aluno.get();
        entity.setNome(alunoRequest.getNome());
        return mapper.toResponse(entity);
    }

    @Transactional
    public void delete(int id) {
        log.info("Deletando aluno id - {}", id);
        Optional<Aluno> aluno = repository.findByIdOptional(id);
        aluno.ifPresent(repository::delete);
    }


    @Transactional
    public TutorResponse updateTutor(int idAluno, int idProfessor) {

        log.info("Atualizando tutor do aluno-id: {}, professor-id: {}", idAluno, idProfessor);

        var aluno = repository.findById(idAluno);
        var professor = professorRepository.findById(idProfessor);

        if (Objects.isNull(aluno)) throw new EntityNotFoundException("Aluno não encontrado");
        if (Objects.isNull(professor)) throw new EntityNotFoundException("Professor não encontrado");
     
        aluno.setTutor(professor);
        repository.persist(aluno);

        return mapper.toResponse(professor);
    }

    public List<AlunoResponse> getAlunoByProfessorId(int idProfessor) {

        log.info("Buscando alunos tutorados pelo professor-id: {}", idProfessor);
        
        var professor = professorRepository.findById(idProfessor);
        if (Objects.isNull(professor)) throw new EntityNotFoundException("Professor não encontrado");
        
        var query = repository.find("tutor", professor);
        if (query.count() == 0) throw new EntityNotFoundException("Não foram encontrados alunos tutorados pelo professor");
        
        var aluno = query.list();
        
        return mapper.toResponse(aluno);
        
    }

}
