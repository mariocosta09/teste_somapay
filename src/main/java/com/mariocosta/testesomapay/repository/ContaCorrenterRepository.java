package com.mariocosta.testesomapay.repository;

import com.mariocosta.testesomapay.model.ContaCorrente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContaCorrenterRepository extends JpaRepository<ContaCorrente,Integer> {
}
