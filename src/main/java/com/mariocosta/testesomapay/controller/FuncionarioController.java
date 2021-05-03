package com.mariocosta.testesomapay.controller;

import com.mariocosta.testesomapay.model.entity.ContaCorrente;
import com.mariocosta.testesomapay.model.entity.Empresa;
import com.mariocosta.testesomapay.model.entity.Funcionario;
import com.mariocosta.testesomapay.model.entity.model.repository.ContaCorrenterRepository;
import com.mariocosta.testesomapay.model.entity.model.repository.EmpresaRepository;
import com.mariocosta.testesomapay.model.entity.model.repository.FuncionarioRepository;
import com.mariocosta.testesomapay.controller.dto.FuncionarioDTO;
import com.mariocosta.testesomapay.service.FuncionarioService;
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
@RequestMapping("/api/funcionario")
@RequiredArgsConstructor
public class FuncionarioController {

    private final EmpresaRepository empresaRepository;
    private final ContaCorrenterRepository contaCorrenteRepository;
    private final FuncionarioRepository funcionarioRepository;

    @Autowired
    private FuncionarioService funcionarioService;

    @GetMapping
    public List<Funcionario> getFuncionarios(){
        return funcionarioRepository.findAll();
    }

    @GetMapping("{id}")
    public Funcionario getFuncionarioById(@PathVariable Integer id){
        return  funcionarioRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Funcionario não encontrado"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Funcionario create(@RequestBody @Valid FuncionarioDTO dto){

        ContaCorrente conta =   funcionarioService.createContaCorrente(dto.getNumero_agencia(),dto.getNumero_conta(), dto.getSaldo(), dto.getTipo_conta());

        Optional<ContaCorrente> contaCorrenteOptional = contaCorrenteRepository.findById(conta.getId());
        ContaCorrente contaCorrente = contaCorrenteOptional.orElse(new ContaCorrente());

        Integer id_empresa =  dto.getId_empresa();

        Empresa empresa =
                empresaRepository.findById(id_empresa)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Empresa Não existe"));

        Funcionario funcionario = new Funcionario();
        funcionario.setNome(dto.getNome());
        funcionario.setCpf(dto.getCpf());
        funcionario.setEndereco(dto.getEndereco());
        funcionario.setEmpresa(empresa);
        funcionario.setContaCorrente(contaCorrente);

        return funcionarioRepository.save(funcionario);

    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public  void deleteFuncionario(@PathVariable Integer id){
        funcionarioRepository
                .findById(id)
                .map(empresa -> {
                    funcionarioRepository.delete(empresa);
                    return Void.TYPE;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Funcionario não encontrado"));
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateFuncionario(@PathVariable Integer id, @RequestBody @Valid FuncionarioDTO funcionarioUpdate){
        funcionarioRepository
                .findById(id)
                .map(funcionario -> {

                    funcionario.setNome(funcionarioUpdate.getNome());
                    funcionario.setCpf(funcionarioUpdate.getCpf());
                    funcionario.setEndereco(funcionarioUpdate.getEndereco());

                    ContaCorrente contaCorrente = funcionario.getContaCorrente();

                    funcionarioService.updateContaCorrente(contaCorrente,funcionarioUpdate);
                    return funcionarioRepository.save(funcionario);
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    }

    @GetMapping("/saldo/{id}")
    public ResponseEntity<Object> getSaldoFuncionario(@PathVariable Integer id){
        Integer saldoFuncionario = funcionarioRepository
                .findById(id)
                .map(saldo -> {
                    return  saldo.getContaCorrente().getSaldo();
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Funcionario não encontrado"));

        return ResponseEntity.status(HttpStatus.CREATED).body(
                Collections.singletonMap("saldo_funcionario", saldoFuncionario));
    }


}
