package com.study.quarkus.repository;

import com.study.quarkus.model.Aluno;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AlunoRepository implements PanacheRepositoryBase<Aluno, Integer> {

}
