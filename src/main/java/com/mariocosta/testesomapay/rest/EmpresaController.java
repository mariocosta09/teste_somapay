package com.mariocosta.testesomapay.rest;

import com.mariocosta.testesomapay.model.entity.ContaCorrente;
import com.mariocosta.testesomapay.model.entity.Empresa;
import com.mariocosta.testesomapay.model.entity.model.repository.ContaCorrenterRepository;
import com.mariocosta.testesomapay.model.entity.model.repository.EmpresaRepository;
import com.mariocosta.testesomapay.rest.dto.EmpresaDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/empresas")
@RequiredArgsConstructor
public class EmpresaController {

    private final EmpresaRepository repository;
    private final ContaCorrenterRepository contaCorrenteRepository;

    @GetMapping
    public List<Empresa> getEmpresas(){
        return repository.findAll();
    }


    @GetMapping("{id}")
    public Empresa getEmpresaById(@PathVariable Integer id){
        return  repository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }



    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Empresa create(@RequestBody EmpresaDTO dto){
      ContaCorrente conta =   this.createContaCorrente(dto.getNumero_agencia(),dto.getNumero_conta(), dto.getSaldo(), dto.getTipo_conta());

        Optional<ContaCorrente>contaCorrenteOptional = contaCorrenteRepository.findById(conta.getId());
        ContaCorrente contaCorrente = contaCorrenteOptional.orElse(new ContaCorrente());

        Empresa empresa = new Empresa();
        empresa.setRazao_social(dto.getRazao_social());
        empresa.setCnpj(dto.getCnpj());
        empresa.setEndereco(dto.getEndereco());
        empresa.setContaCorrente(contaCorrente);

        return  repository.save(empresa);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public  void deleteEmpresa(@PathVariable Integer id){
        repository
                .findById(id)
                .map(empresa -> {
                    repository.delete(empresa);
                    return Void.TYPE;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateEmpresa(@PathVariable Integer id, @RequestBody EmpresaDTO empresaUpdate){
        repository
                .findById(id)
                .map(empresa -> {

                    empresa.setRazao_social(empresaUpdate.getRazao_social());
                    empresa.setCnpj(empresaUpdate.getCnpj());
                    empresa.setEndereco(empresaUpdate.getEndereco());

                    ContaCorrente contaCorrente = empresa.getContaCorrente();

                    this.updateContaCorrente(contaCorrente,empresaUpdate);
                    return repository.save(empresa);
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    }


   @GetMapping("/saldo/{id}")
   public ResponseEntity<Object> getSaldoEmpresa(@PathVariable Integer id){
       Integer saldoEmpresa = repository
               .findById(id)
               .map(saldo -> {
                   return  saldo.getContaCorrente().getSaldo();
               })
               .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

       return ResponseEntity.status(HttpStatus.CREATED).body(
               Collections.singletonMap("saldo_empresa", saldoEmpresa));
   }


    public ContaCorrente createContaCorrente(Integer numero_agencia, String numero_conta, Integer saldo, String tipo_conta){
        ContaCorrente contaCorrente = new ContaCorrente();
        contaCorrente.setNumero_agencia(numero_agencia);
        contaCorrente.setNumero_conta(numero_conta);
        contaCorrente.setSaldo(saldo);
        contaCorrente.setTipo_conta(tipo_conta);
        return  contaCorrenteRepository.save(contaCorrente);

    }

    public void updateContaCorrente(ContaCorrente conta, EmpresaDTO empresaUpdate){

        contaCorrenteRepository.findById(conta.getId())
                .map(contaCorrente -> {
                    contaCorrente.setNumero_conta(empresaUpdate.getNumero_conta());
                    contaCorrente.setNumero_agencia(empresaUpdate.getNumero_agencia());
                    contaCorrente.setTipo_conta(empresaUpdate.getTipo_conta());
                    contaCorrente.setSaldo(empresaUpdate.getSaldo());
                    return contaCorrenteRepository.save(contaCorrente);
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    }


}
