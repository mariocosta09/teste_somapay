package com.mariocosta.testesomapay.util;

import com.mariocosta.testesomapay.model.entity.ContaCorrente;
import com.mariocosta.testesomapay.model.entity.model.repository.ContaCorrenterRepository;
import com.mariocosta.testesomapay.rest.dto.EmpresaDTO;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


@RequiredArgsConstructor
public class HelperContaCorrente {
    private final ContaCorrenterRepository contaCorrenteRepository;

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
