package com.manuel.hospital.citas.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manuel.hospital.citas.dto.CitaActualizadaDTO;
import com.manuel.hospital.citas.dto.ReservaCitaDto;
import com.manuel.hospital.citas.dto.CitaDto;
import com.manuel.hospital.citas.dto.DoctorDto;
import com.manuel.hospital.citas.dto.PacienteDto;
import com.manuel.hospital.citas.entity.Citas;
import com.manuel.hospital.citas.services.CitaServicio;

@RestController
@RequestMapping("/citas")
public class CitaController {

	
private CitaServicio citaServicio;
	
@Autowired
public CitaController(CitaServicio citaServicio) {
		super();
		this.citaServicio = citaServicio;
	}


@GetMapping("/listar")
public ResponseEntity<List<Citas>>listarCitas(){

	return ResponseEntity.ok(citaServicio.listarCitas());
}

@GetMapping("/listar/{id}")
public ResponseEntity<Object>listarPorId(@PathVariable Long id){
	
	try {
		return ResponseEntity.ok(citaServicio.listarPorId(id));
	}catch (Exception e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La cita no existe");
	}
}

@PutMapping("/actualizarCitas/{id}")

public ResponseEntity<Citas>actualizarCitas(@PathVariable Long id,@RequestBody CitaActualizadaDTO citaActualizadaDTO){
	
Citas citaActualizada=	citaServicio.actualizarCitaPorId(
		id,
		citaActualizadaDTO.getPacienteId(),
		citaActualizadaDTO.getDoctorId(),
		citaActualizadaDTO.getEstado());
		
return ResponseEntity.ok(citaActualizada);
}

	
	
	


@PostMapping("/crearCita")
public ResponseEntity<Citas>crearCita( @RequestBody CitaDto cita){
	
	Citas citaFinal = new Citas();
	//1.LLamar al microservicio de paciente pasandole como parametro de entrada un dni. Te devuelve un objeto paciente y de aqui le pillamos el pacienteId
	
	PacienteDto pacienteDto = citaServicio.buscarPaciente(cita.getDniPaciente());	
	//2. Llamar al microservicio de doctor pasandole el nombre y apellidos como parametros de entrada, Esto devuelve un d
	
	
	DoctorDto doctorDto=citaServicio.buscarDoctor(cita.getNombreDoctor(), cita.getApellidosDoctor());
	
	
	//3. Construir el objeto cita con el pacienteId obtenido en el paso 1, el doctorId obtenido en el paso 2 y el estado que lo cojo del CitaDto
	citaFinal.setPacienteId(pacienteDto.getId());
	citaFinal.setDoctorId(doctorDto.getId());
	citaFinal.setEstado(cita.getEstado());
	return ResponseEntity.ok(citaServicio.crearCita(citaFinal));
	
}


	
@DeleteMapping("/{citaId}")
	
public ResponseEntity<Void>eliminarCita(@PathVariable Long citaId){
	
	citaServicio.eliminarCita(citaId);
	
	return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	
}



//FeignClient

@PostMapping("/crear")
public ResponseEntity<Citas> crear(@RequestBody CitaDto citaDTO) {
	
	//Obtenemos el paciente por dni y el doctor por apellidos y nombre
	PacienteDto paciente=citaServicio.buscarPacienteFeign(citaDTO.getDniPaciente());
	DoctorDto doctor=citaServicio.buscarDoctorFeign(citaDTO.getNombreDoctor(),citaDTO.getApellidosDoctor());
	
	 if (paciente == null) {
         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Devuelve 404 si no se encontró el paciente
     }
     
     if (doctor == null) {
         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Devuelve 404 si no se encontró el doctor
     }
	
	//Creamos la cita
	
	Citas citaNueva=new Citas();
	citaNueva.setPacienteId(paciente.getId());
	citaNueva.setDoctorId(doctor.getId());
	citaNueva.setEstado(citaDTO.getEstado());
	
	return ResponseEntity.ok(citaServicio.crearCita(citaNueva));

	
}

// Buscar por nombre

@PostMapping("/crearCitaNombrePaciente")
public ResponseEntity<List<Citas>> crearListaCitas(@RequestBody ReservaCitaDto reservaCitaDto){
	
	List<PacienteDto> pacientes=citaServicio.buscarPacientePoNombre(reservaCitaDto.getNombrePaciente());

	 if (pacientes == null|| pacientes.isEmpty()) {
       return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Devuelve 404 si no se encontró el paciente
     }
	 
	 List<Citas> listaCitas= new ArrayList<>();
	 for(PacienteDto p:pacientes) {
		Citas citaNueva=new Citas();
		citaNueva.setPacienteId(p.getId());
		citaNueva.setEstado(reservaCitaDto.getEstado());
		Citas citaCreada = citaServicio.crearCita(citaNueva);
		listaCitas.add(citaCreada);
		 
	 }
	 
	
		return ResponseEntity.ok(listaCitas);
	
	 
}


}
