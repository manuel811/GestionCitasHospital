package com.manuel.hospital.citas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CitaDto {
	
	
	private String nombreDoctor;
	private String apellidosDoctor;
	private String dniPaciente;
	private String estado;
	


	
	
}
