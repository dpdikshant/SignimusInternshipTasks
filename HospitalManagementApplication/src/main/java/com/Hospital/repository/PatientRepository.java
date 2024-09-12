package com.Hospital.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Hospital.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, Integer> {

	Optional<Patient> findById(int id);

	List<Patient> findAll();

	Patient save(Patient patient);

	void deleteById(int id);

	

}
