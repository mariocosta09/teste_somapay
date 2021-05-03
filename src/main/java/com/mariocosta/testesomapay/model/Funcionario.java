package com.mariocosta.testesomapay.model;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Data
public class Funcionario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id;

    @Column(nullable = false, length = 255)
    @NotEmpty(message = "{campo.nome.obrigatorio}")
    private  String nome;

    @Column(nullable = false, unique = true, length = 20)
    @NotNull(message = "{campo.cpf.obrigatorio}")
    @CPF(message = "Cpf invalido")
    private String cpf;

    @Column(nullable = false, length = 255)
    @NotEmpty(message = "{campo.endereco.obrigatorio}")
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
