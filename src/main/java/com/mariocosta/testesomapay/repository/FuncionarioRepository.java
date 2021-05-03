package com.mariocosta.testesomapay.repository;

import com.mariocosta.testesomapay.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncionarioRepository extends JpaRepository<Funcionario,Integer> {
}
