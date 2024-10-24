package com.manuel.hospital.citas.dto;

import lombok.Data;

@Data
public class CitaActualizadaDTO {
	
	private Long pacienteId;
    private Long doctorId;
    private String estado;
}
