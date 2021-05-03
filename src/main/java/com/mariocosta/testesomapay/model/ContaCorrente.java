package com.mariocosta.testesomapay.model;
import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Entity
@Data
public class ContaCorrente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50)
    private String numero_conta;

    @Column(nullable = false,  length = 50)
    private Integer numero_agencia;

    @Column(nullable = false, length = 2)
    private String tipo_conta;

    @Column(nullable = false)
    private Integer saldo;

    @Column
    private LocalDate data_cadastro;

    @PrePersist
    public void prePersist(){
        setData_cadastro(LocalDate.now());
    }

}
