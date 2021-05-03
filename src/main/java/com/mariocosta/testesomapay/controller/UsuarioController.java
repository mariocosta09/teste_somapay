package com.mariocosta.testesomapay.controller;

import com.mariocosta.testesomapay.model.Usuario;
import com.mariocosta.testesomapay.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/usuario")
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioRepository usuarioRepository;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario createUsuario(@RequestBody @Valid Usuario usuario){
            usuarioRepository.save(usuario);
            return usuario;
    }

}
