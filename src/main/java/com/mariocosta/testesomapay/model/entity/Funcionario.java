package com.mariocosta.testesomapay.model.entity;
import lombok.Data;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class Funcionario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id;

    @Column(nullable = false, length = 255)
    private  String nome;

    @Column(nullable = false, unique = true, length = 20)
    private String cpf;

    @Column(nullable = false, length = 255)
    private String endereco;

    @Column
    private LocalDate data_cadastro;

    @ManyToOne
    @JoinColumn(name="id_conta", nullable=false)
    private ContaCorrente contaCorrente;

    @ManyToOne
    @JoinColumn(name="id_empresa", nullable=false)
    private Empresa empresa;

    @PrePersist
    public void prePersist(){
        setData_cadastro(LocalDate.now());
    }

}
