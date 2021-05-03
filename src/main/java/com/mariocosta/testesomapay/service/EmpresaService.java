package com.mariocosta.testesomapay.service;

import com.mariocosta.testesomapay.controller.dto.EmpresaDTO;
import com.mariocosta.testesomapay.controller.dto.FolhaPagamentoDTO;
import com.mariocosta.testesomapay.model.ContaCorrente;
import com.mariocosta.testesomapay.model.Empresa;
import com.mariocosta.testesomapay.model.Funcionario;
import com.mariocosta.testesomapay.repository.ContaCorrenterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class EmpresaService {
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

    public void atualizaSaldo(Optional<Empresa> empresa, Integer totalfolha,  double taxa){

        double valorTotal = empresa.get().getContaCorrente().getSaldo() - this.calcularTotalDescontos(totalfolha,taxa);
        contaCorrenteRepository.findById(empresa.get().getContaCorrente().getId())
                .map(contaCorrente -> {
                    contaCorrente.setNumero_conta(empresa.get().getContaCorrente().getNumero_conta());
                    contaCorrente.setNumero_agencia(empresa.get().getContaCorrente().getNumero_agencia());
                    contaCorrente.setTipo_conta(empresa.get().getContaCorrente().getTipo_conta());
                    contaCorrente.setSaldo((int) valorTotal);
                    return contaCorrenteRepository.save(contaCorrente);
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    }

    public Double calcularTotalDescontos(Integer totalFolha,double taxa){
        double totalDescontos = totalFolha + taxa;
        return  totalDescontos;
    }

    public void pagamentoFuncionario(Funcionario funcionario, FolhaPagamentoDTO folhaPagamentoDTO){
        contaCorrenteRepository.findById(funcionario.getContaCorrente().getId())
                .map(contaCorrente -> {
                    contaCorrente.setNumero_conta(funcionario.getContaCorrente().getNumero_conta());
                    contaCorrente.setNumero_agencia(funcionario.getContaCorrente().getNumero_agencia());
                    contaCorrente.setTipo_conta(funcionario.getContaCorrente().getTipo_conta());
                    contaCorrente.setSaldo(folhaPagamentoDTO.getSalario());
                    return contaCorrenteRepository.save(contaCorrente);
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }


}
