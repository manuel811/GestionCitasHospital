package com.manuel.hospital.citas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableFeignClients
public class MicroservicioCitasApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroservicioCitasApplication.class, args);
		
	
	}
	


}
