package com.mariocosta.testesomapay.rest;

import com.mariocosta.testesomapay.model.entity.ContaCorrente;
import com.mariocosta.testesomapay.model.entity.Empresa;
import com.mariocosta.testesomapay.model.entity.Funcionario;
import com.mariocosta.testesomapay.model.entity.model.repository.ContaCorrenterRepository;
import com.mariocosta.testesomapay.model.entity.model.repository.EmpresaRepository;
import com.mariocosta.testesomapay.model.entity.model.repository.FuncionarioRepository;
import com.mariocosta.testesomapay.rest.dto.EmpresaDTO;
import com.mariocosta.testesomapay.rest.dto.FuncionarioDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/funcionario")
@RequiredArgsConstructor
public class FuncionarioController {

    private final EmpresaRepository empresaRepository;
    private final ContaCorrenterRepository contaCorrenteRepository;
    private final FuncionarioRepository funcionarioRepository;

    @GetMapping
    public List<Funcionario> getFuncionarios(){
        return funcionarioRepository.findAll();
    }

    @GetMapping("{id}")
    public Funcionario getFuncionarioById(@PathVariable Integer id){
        return  funcionarioRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Funcionario cerate(@RequestBody FuncionarioDTO dto){

        ContaCorrente conta =   this.createContaCorrente(dto.getNumero_agencia(),dto.getNumero_conta(), dto.getSaldo(), dto.getTipo_conta());

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
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateFuncionario(@PathVariable Integer id, @RequestBody FuncionarioDTO funcionarioUpdate){
        funcionarioRepository
                .findById(id)
                .map(funcionario -> {

                    funcionario.setNome(funcionarioUpdate.getNome());
                    funcionario.setCpf(funcionarioUpdate.getCpf());
                    funcionario.setEndereco(funcionarioUpdate.getEndereco());

                    ContaCorrente contaCorrente = funcionario.getContaCorrente();

                    this.updateContaCorrente(contaCorrente,funcionarioUpdate);
                    return funcionarioRepository.save(funcionario);
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    }



    public ContaCorrente createContaCorrente(Integer numero_agencia, String numero_conta, Integer saldo, String tipo_conta){
        ContaCorrente contaCorrente = new ContaCorrente();
        contaCorrente.setNumero_agencia(numero_agencia);
        contaCorrente.setNumero_conta(numero_conta);
        contaCorrente.setSaldo(saldo);
        contaCorrente.setTipo_conta(tipo_conta);
        return  contaCorrenteRepository.save(contaCorrente);

    }

    public void updateContaCorrente(ContaCorrente conta, FuncionarioDTO funcionarioUpdate){

        contaCorrenteRepository.findById(conta.getId())
                .map(contaCorrente -> {
                    contaCorrente.setNumero_conta(funcionarioUpdate.getNumero_conta());
                    contaCorrente.setNumero_agencia(funcionarioUpdate.getNumero_agencia());
                    contaCorrente.setTipo_conta(funcionarioUpdate.getTipo_conta());
                    contaCorrente.setSaldo(funcionarioUpdate.getSaldo());
                    return contaCorrenteRepository.save(contaCorrente);
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    }
}
