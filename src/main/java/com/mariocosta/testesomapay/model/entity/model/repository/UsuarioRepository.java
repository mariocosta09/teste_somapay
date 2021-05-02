package com.mariocosta.testesomapay.model.entity.model.repository;

import com.mariocosta.testesomapay.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario,Integer> {
}
