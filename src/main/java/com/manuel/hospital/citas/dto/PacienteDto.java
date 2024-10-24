package com.manuel.hospital.citas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PacienteDto {

	private Long id;
	private String nombre;
	private String apellidos;
	private String dni;
	private String direccion;
	private String telefono;
}
