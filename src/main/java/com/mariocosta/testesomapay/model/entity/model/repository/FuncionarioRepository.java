package com.mariocosta.testesomapay.model.entity.model.repository;

import com.mariocosta.testesomapay.model.entity.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncionarioRepository extends JpaRepository<Funcionario,Integer> {
}
