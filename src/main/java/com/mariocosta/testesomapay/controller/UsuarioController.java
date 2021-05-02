package com.mariocosta.testesomapay.controller;

import com.mariocosta.testesomapay.model.entity.Usuario;
import com.mariocosta.testesomapay.model.entity.model.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuario")
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioRepository usuarioRepository;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createUsuario(@RequestBody @Validated Usuario usuario){
            usuarioRepository.save(usuario);
    }

}
