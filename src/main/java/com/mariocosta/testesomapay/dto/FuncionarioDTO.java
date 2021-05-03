package com.mariocosta.testesomapay.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FuncionarioDTO {

    private String nome;
    private  String cpf;
    private String endereco;
    private Integer id_conta;
    private Integer id_empresa;

    private Integer numero_agencia;
    private String numero_conta;
    private Integer saldo;
    private String  tipo_conta;
}
