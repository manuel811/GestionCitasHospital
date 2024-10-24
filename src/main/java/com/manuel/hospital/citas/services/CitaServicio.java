package com.manuel.hospital.citas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.manuel.hospital.citas.dto.CitaDto;
import com.manuel.hospital.citas.dto.DoctorDto;
import com.manuel.hospital.citas.dto.PacienteDto;
import com.manuel.hospital.citas.entity.Citas;
import com.manuel.hospital.citas.feignclient.DoctorFeignClient;
import com.manuel.hospital.citas.feignclient.PacienteFeignClient;
import com.manuel.hospital.citas.repository.CitaRepositorio;

@Service
public class CitaServicio {
	
@Autowired
private PacienteFeignClient pacienteFeignClient;

@Autowired
private DoctorFeignClient doctorFeignClient;
	
	
@Autowired
private RestTemplate restTemplate;


@Autowired
private CitaRepositorio citaRepositorio;
	

//Metodos

public List<Citas>listarCitas(){
	return citaRepositorio.findAll();
}

public Citas listarPorId(Long id) {
	
	return citaRepositorio.findById(id).get();
}


public Citas actualizarCitaPorId(Long id,Long pacienteId,Long doctorId, String estado) {
	
	Citas citaActualizada=citaRepositorio.findById(id).get();
	
	citaActualizada.setPacienteId(pacienteId);
	citaActualizada.setDoctorId(doctorId);
	citaActualizada.setEstado(estado);
	Citas citaFinal=citaRepositorio.save(citaActualizada);
	
	return citaFinal;
	
	
}

	
public Citas crearCita(Citas cita) {
	
	return citaRepositorio.save(cita);
}

public void eliminarCita(Long id) {
	
	citaRepositorio.deleteById(id);
	
}

public List<Citas>listaCitasPorPacientes(Long pacienteId){
	
	return citaRepositorio.findByPacienteId(pacienteId);
}
	


//RestTemplate

public PacienteDto buscarPaciente(String dni) {
	
	String url="http://localhost:8001/paciente/dni/"+dni;
	 ResponseEntity<PacienteDto>response=restTemplate.getForEntity(url, PacienteDto.class);
	 return response.getBody();

}


public DoctorDto buscarDoctor(String nombre,String apellidos) {
	String url="http://localhost:8002/doctor/nombre/" + nombre +"/apellidos/"+ apellidos;
	 ResponseEntity<DoctorDto>response=restTemplate.getForEntity(url, DoctorDto.class);
	 return response.getBody();
}

//FeignClient

public PacienteDto buscarPacienteFeign(String dni) {
	
	return pacienteFeignClient.obtenerPacientePorDni(dni);
	
}

public DoctorDto buscarDoctorFeign(String nombre, String apellidos) {
	return doctorFeignClient.findNameAndSurname(nombre, apellidos);
}


//

public List<PacienteDto> buscarPacientePoNombre(String nombre) {
	return pacienteFeignClient.obtenerPorNombrePaciente(nombre);
}


	
}
