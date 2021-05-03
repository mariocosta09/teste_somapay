package com.mariocosta.testesomapay.service;

import com.mariocosta.testesomapay.dto.FolhaPagamentoDTO;
import com.mariocosta.testesomapay.model.Empresa;
import com.mariocosta.testesomapay.model.Funcionario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class FolhaPagamento {
    @Autowired
    private EmpresaService empresaService;


    public ResponseEntity<Object> sendPagamento(Integer totalfolha, List<Funcionario> funcionarios, FolhaPagamentoDTO folhaPagamentoDTO, Optional<Empresa> empresa) {

        if (totalfolha > empresa.get().getContaCorrente().getSaldo()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                    Collections.singletonMap("Error", "Saldo insuficiente"));
        } else {
            this.efetuaPagamentoFuncionarios(funcionarios, folhaPagamentoDTO);
            double taxaAdministracao = this.calcularTaxa(totalfolha);
            empresaService.atualizaSaldo(empresa, totalfolha, taxaAdministracao);

            return ResponseEntity.status(HttpStatus.CREATED).body(
                    Collections.singletonMap("Total_folha_pagamento", totalfolha)


            );
        }

    }

    public void efetuaPagamentoFuncionarios(List<Funcionario> funcionarios, FolhaPagamentoDTO folhaPagamentoDTO) {
        for (Funcionario funcionario : funcionarios) {
            empresaService.pagamentoFuncionario(funcionario, folhaPagamentoDTO);
        }

    }


    public Integer calculaFolhaPagamento(List<Funcionario> funcionarios, FolhaPagamentoDTO folhaPagamentoDTO) {
        Integer countSalario = Math.toIntExact(funcionarios.stream().count());
        Integer totalFolhaPagamento = countSalario * folhaPagamentoDTO.getSalario();

        return totalFolhaPagamento;
    }

    public double calcularTaxa(Integer total_folha) {
        double valoTaxa = total_folha * 0.38 / 100;
        return valoTaxa;
    }


    public Double calcularTotalDescontos(Integer totalFolha, double taxa) {
        double totalDescontos = totalFolha + taxa;
        return totalDescontos;
    }


}
