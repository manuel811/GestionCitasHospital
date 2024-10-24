package com.manuel.hospital.citas.feignclient;

import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import com.manuel.hospital.citas.dto.DoctorDto;




@FeignClient(name = "microservicios-doctor",url="http://localhost:8002/doctor")

public interface DoctorFeignClient {

	
	@GetMapping("/nombre/{nombre}/apellidos/{apellidos}")
	public DoctorDto findNameAndSurname(@PathVariable String nombre, @PathVariable String apellidos);
	
}
