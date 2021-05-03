package com.mariocosta.testesomapay.controller;

import com.mariocosta.testesomapay.model.ContaCorrente;
import com.mariocosta.testesomapay.repository.ContaCorrenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/contas")
public class ContaCorrenteController {


    private final ContaCorrenterRepository repository;

    @Autowired
    public ContaCorrenteController(ContaCorrenterRepository repository){
        this.repository = repository;
    }

    @PostMapping
    @ResponseStatus
    public ContaCorrente create(ContaCorrente contaCorrente){
            return  repository.save(contaCorrente);
    }
}
