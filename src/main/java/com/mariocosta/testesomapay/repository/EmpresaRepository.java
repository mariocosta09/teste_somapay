package com.mariocosta.testesomapay.repository;

import com.mariocosta.testesomapay.model.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpresaRepository extends JpaRepository<Empresa, Integer> {
}
