package com.mariocosta.testesomapay.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    @NotEmpty(message = "Campo username e obrigatorio")
    private String username;

    @Column(unique = true, name = "email")
    @NotEmpty(message = "Campo Email e obrigatorio")
    @Email(message = "Email invalido")
    private String email;

    @Column
    @NotNull(message = "Campo password e obrigatorio")
    private String password;
}
