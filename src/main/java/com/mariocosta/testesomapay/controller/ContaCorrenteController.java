package com.mariocosta.testesomapay.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/contas")
public class ContaCorrenteController {

    @GetMapping
    public  String hello(){
        return  "Hellou contas";
    }
}
