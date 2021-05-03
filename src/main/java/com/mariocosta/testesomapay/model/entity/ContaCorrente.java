package com.mariocosta.testesomapay.model.entity;
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

    @Column(nullable = false,unique = true, length = 50)
    @NotEmpty(message = "{campo.conta.invalido}")
    private String numero_conta;

    @Column(nullable = false, unique = true, length = 50)
    @NotEmpty(message = "{campo.agencia.invalido}")
    private Integer numero_agencia;

    @Column(nullable = false, length = 2)
    @NotEmpty(message = "{campo.tipo_cont.invalido}")
    private String tipo_conta;

    @Column(nullable = false)
    @NotEmpty(message = "{campo.saldo.invalido}")
    private Integer saldo;

    @Column
    private LocalDate data_cadastro;


    @PrePersist
    public void prePersist(){
        setData_cadastro(LocalDate.now());
    }

}
