package com.mariocosta.testesomapay.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmpresaDTO {
    private String razao_social;
    private  String cnpj;
    private String endereco;
    private Integer id_conta;

    private Integer numero_agencia;
    private String numero_conta;
    private Integer saldo;
    private String  tipo_conta;

}
