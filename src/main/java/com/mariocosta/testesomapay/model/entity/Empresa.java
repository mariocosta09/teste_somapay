package com.mariocosta.testesomapay.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
    private  String razao_social;

    @Column(nullable = false,unique = true, length = 20)
    private String cnpj;

    @Column(nullable = false, length = 255)
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
