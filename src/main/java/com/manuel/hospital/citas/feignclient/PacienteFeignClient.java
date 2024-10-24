package com.manuel.hospital.citas.feignclient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.manuel.hospital.citas.dto.PacienteDto;


@FeignClient(name="microservicios-pacientes", url="http://localhost:8001/paciente")
public interface PacienteFeignClient {
	
	
	@GetMapping("/dni/{dni}")
	public PacienteDto obtenerPacientePorDni(@PathVariable String dni);
	
	
	@GetMapping("/nombre/{nombre}")
	public List<PacienteDto> obtenerPorNombrePaciente(@PathVariable("nombre") String nombre);

}
