package com.manuel.hospital.citas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.manuel.hospital.citas.entity.Citas;

@Repository
public interface CitaRepositorio extends JpaRepository<Citas, Long>{

	List<Citas>findByPacienteId(Long pacienteId);
}
