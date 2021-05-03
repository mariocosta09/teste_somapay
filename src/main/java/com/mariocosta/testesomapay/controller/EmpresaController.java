package com.mariocosta.testesomapay.controller;

import com.mariocosta.testesomapay.model.entity.ContaCorrente;
import com.mariocosta.testesomapay.model.entity.Empresa;
import com.mariocosta.testesomapay.model.entity.Funcionario;
import com.mariocosta.testesomapay.model.entity.model.repository.ContaCorrenterRepository;
import com.mariocosta.testesomapay.model.entity.model.repository.EmpresaRepository;
import com.mariocosta.testesomapay.model.entity.model.repository.FuncionarioRepository;
import com.mariocosta.testesomapay.controller.dto.EmpresaDTO;
import com.mariocosta.testesomapay.controller.dto.FolhaPagamentoDTO;
import com.mariocosta.testesomapay.service.EmpresaService;
import com.mariocosta.testesomapay.service.FolhaPagamento;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/empresas")
@RequiredArgsConstructor
public class EmpresaController {

    private final EmpresaRepository repository;
    private final ContaCorrenterRepository contaCorrenteRepository;
    private final FuncionarioRepository funcionarioRepository;

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private FolhaPagamento folhaPagamento;

    @GetMapping
    public List<Empresa> getEmpresas() {
        return repository.findAll();
    }

    @GetMapping("{id}")
    public Empresa getEmpresaById(@PathVariable Integer id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Empresa não encontrada"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Empresa create(@RequestBody @Valid EmpresaDTO dto) {


        ContaCorrente conta = empresaService.createContaCorrente(dto.getNumero_agencia(), dto.getNumero_conta(), dto.getSaldo(), dto.getTipo_conta());

        Optional<ContaCorrente> contaCorrenteOptional = contaCorrenteRepository.findById(conta.getId());
        ContaCorrente contaCorrente = contaCorrenteOptional.orElse(new ContaCorrente());

        Empresa empresa = new Empresa();
        empresa.setRazao_social(dto.getRazao_social());
        empresa.setCnpj(dto.getCnpj());
        empresa.setEndereco(dto.getEndereco());
        empresa.setContaCorrente(contaCorrente);

        return repository.save(empresa);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmpresa(@PathVariable Integer id) {
        repository
                .findById(id)
                .map(empresa -> {
                    repository.delete(empresa);
                    return Void.TYPE;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empresa não encontrada"));
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateEmpresa(@PathVariable Integer id, @RequestBody @Valid  EmpresaDTO empresaUpdate) {
        repository
                .findById(id)
                .map(empresa -> {

                    empresa.setRazao_social(empresaUpdate.getRazao_social());
                    empresa.setCnpj(empresaUpdate.getCnpj());
                    empresa.setEndereco(empresaUpdate.getEndereco());

                    ContaCorrente contaCorrente = empresa.getContaCorrente();

                    empresaService.updateContaCorrente(contaCorrente, empresaUpdate);
                    return repository.save(empresa);
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empresa não encontrada"));

    }

    @GetMapping("/saldo/{id}")
    public ResponseEntity<Object> getSaldoEmpresa(@PathVariable Integer id) {
        Integer saldoEmpresa = repository
                .findById(id)
                .map(saldo -> {
                    return saldo.getContaCorrente().getSaldo();
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Empresa não encontrada"));

        return ResponseEntity.status(HttpStatus.CREATED).body(
                Collections.singletonMap("saldo_empresa", saldoEmpresa));
    }


    @PostMapping("/pagar-funcionarios")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> pagarFuncionarios(@RequestBody FolhaPagamentoDTO folhaPagamentoDTO) {

        List<Funcionario> funcionarios = funcionarioRepository.findAll();
        Optional<Empresa> empresa = repository.findById(folhaPagamentoDTO.getId_empresa());

        Integer totalfolha = folhaPagamento.calculaFolhaPagamento(funcionarios, folhaPagamentoDTO);
        if (totalfolha > empresa.get().getContaCorrente().getSaldo()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                    Collections.singletonMap("Error", "Saldo insuficiente"));
        } else {
            folhaPagamento.efetuaPagamentoFuncionarios(funcionarios, folhaPagamentoDTO);
            double taxaAdministracao = folhaPagamento.calcularTaxa(totalfolha);
            empresaService.atualizaSaldo(empresa, totalfolha, taxaAdministracao);

            return ResponseEntity.status(HttpStatus.CREATED).body(
                    Collections.singletonMap("Total_folha_pagamento", totalfolha));
        }


    }


}
