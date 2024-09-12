package com.Hospital.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Hospital.model.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Integer> {

	List<Doctor> findAll();

	Doctor save(Doctor doctor);

	Optional<Doctor> findById(int id);

	void deleteById(int id);

}
