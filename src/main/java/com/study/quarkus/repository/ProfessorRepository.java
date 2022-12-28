package com.study.quarkus.repository;

import com.study.quarkus.model.Professor;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProfessorRepository implements PanacheRepositoryBase<Professor, Integer> {
}
