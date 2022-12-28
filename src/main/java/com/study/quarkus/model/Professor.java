package com.study.quarkus.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PROFESSORES_RODOLFO")
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotBlank(message = "O nome do professos deve ser informado (Model)")
    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name="data_atualizacao", nullable = false)
    private LocalDateTime dateTime;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "titular")
    private Disciplina disciplina;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tutor")
    private List<Aluno> alunos;

    @PrePersist
    public void prePersist(){
        setDateTime(LocalDateTime.now());
    }

}