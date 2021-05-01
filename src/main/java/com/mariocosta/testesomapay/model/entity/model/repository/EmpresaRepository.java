package com.mariocosta.testesomapay.model.entity.model.repository;

import com.mariocosta.testesomapay.model.entity.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpresaRepository extends JpaRepository<Empresa, Integer> {
}
