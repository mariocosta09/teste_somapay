package com.mariocosta.testesomapay.service;

import com.mariocosta.testesomapay.controller.dto.FolhaPagamentoDTO;
import com.mariocosta.testesomapay.model.entity.Funcionario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FolhaPagamento {
    @Autowired
    private  EmpresaService empresaService;
    public void efetuaPagamentoFuncionarios(List<Funcionario> funcionarios,FolhaPagamentoDTO folhaPagamentoDTO){
        for (Funcionario funcionario : funcionarios){
            empresaService.pagamentoFuncionario(funcionario,  folhaPagamentoDTO);
        }

    }


    public Integer calculaFolhaPagamento(List<Funcionario> funcionarios, FolhaPagamentoDTO folhaPagamentoDTO){
        Integer countSalario = Math.toIntExact(funcionarios.stream().count());
        Integer totalFolhaPagamento = countSalario * folhaPagamentoDTO.getSalario();

        return totalFolhaPagamento;
    }

    public double calcularTaxa(Integer total_folha){
        double valoTaxa = total_folha * 0.38 / 100;
        return  valoTaxa;
    }
}
