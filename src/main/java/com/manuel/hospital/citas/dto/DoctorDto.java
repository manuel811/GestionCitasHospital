package com.manuel.hospital.citas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorDto {
	

	private Long id;
	private String nombre;
	private String apellidos;
	private String telefono;
	
	private String especialidad;
}
