package com.Hospital.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Hospital.model.Appointment;
import com.Hospital.model.Doctor;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

	List<Appointment> findAll();

	Appointment save(Appointment appointment);

	Optional<Appointment> findById(int id);

	void deleteById(int id); 

}
