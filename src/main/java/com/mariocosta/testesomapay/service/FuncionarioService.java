package com.mariocosta.testesomapay.service;

import com.mariocosta.testesomapay.controller.dto.FuncionarioDTO;
import com.mariocosta.testesomapay.model.entity.ContaCorrente;
import com.mariocosta.testesomapay.model.entity.model.repository.ContaCorrenterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class FuncionarioService {
    private final ContaCorrenterRepository contaCorrenteRepository;

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
