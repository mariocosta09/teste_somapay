package com.mariocosta.testesomapay.model.entity.model.repository;

import com.mariocosta.testesomapay.model.entity.ContaCorrente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContaCorrenterRepository extends JpaRepository<ContaCorrente,Integer> {
}
