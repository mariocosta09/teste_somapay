package com.mariocosta.testesomapay.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CNPJ;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id;

    @Column(nullable = false, length = 255)
    @NotEmpty(message = "{campo.razao_social.obrigatorio}")
    private  String razao_social;

    @Column(nullable = false,unique = true, length = 20)
    @NotNull(message = "{campo.cnpj.obrigatorio}")
    @CNPJ(message = "{campo.cnpj.invalido}")
    private String cnpj;

    @Column(nullable = false, length = 255)
    @NotEmpty(message = "{campo.endereco.obrigatorio}")
    private String endereco;

    @Column
    private LocalDate data_cadastro;

    @ManyToOne
    @JoinColumn(name="id_conta", nullable=false)
    private ContaCorrente contaCorrente;


    @PrePersist
    public void prePersist(){
        setData_cadastro(LocalDate.now());
    }



}
