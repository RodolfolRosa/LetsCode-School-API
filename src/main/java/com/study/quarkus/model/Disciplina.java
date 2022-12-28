package com.study.quarkus.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "DISCIPLINAS_RODOLFO")
public class Disciplina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotBlank(message = "O nome da disciplina deve ser informado (Model)")
    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name="data_atualizacao", nullable = false)
    private LocalDateTime dateTime;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "titular", unique=true)
    private Professor titular;

    @PrePersist
    public void prePersist(){
        setDateTime(LocalDateTime.now());
    }

}
