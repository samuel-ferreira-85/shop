package com.samuel.workershop;

import com.samuel.workershop.service.CepService;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@EnableFeignClients
@EnableRabbit
@SpringBootApplication
public class WorkershopApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorkershopApplication.class, args);
	}


}
