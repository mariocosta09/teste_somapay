package com.mariocosta.testesomapay.model.entity.model.repository;

import com.mariocosta.testesomapay.model.entity.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.stream.Stream;

public interface FuncionarioRepository extends JpaRepository<Funcionario,Integer> {
}
