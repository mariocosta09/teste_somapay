package com.mariocosta.testesomapay;

import com.mariocosta.testesomapay.model.entity.ContaCorrente;
import com.mariocosta.testesomapay.model.entity.model.repository.ContaCorrenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@SpringBootApplication
public class TestesomapayApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestesomapayApplication.class, args);
	}

}
