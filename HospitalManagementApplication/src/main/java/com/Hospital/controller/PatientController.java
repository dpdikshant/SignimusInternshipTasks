package com.Hospital.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Hospital.model.Patient;
import com.Hospital.services.PatientServices;

@RestController
@RequestMapping("/patients")
public class PatientController {
	
	@Autowired
	private PatientServices pService;
	
	@GetMapping("/list")
	public List<Patient> getAllPatients()
	{
		
		List<Patient> patList = pService.getAllPatient();
		return patList ;
	}
	
	@PostMapping("/savePatient")
	public Patient savePatient(@RequestBody Patient patient)
	{
		Patient pat = pService.savePatient(patient);
		return pat;
	}
	
	@GetMapping("/findPatient/{id}")
	public Patient patientId(@PathVariable int id)
	{
		Patient pat = pService.findById(id);
		return pat;
	}

	@DeleteMapping("delPatient/{id}")
	public void deletePatientById(@PathVariable int id)
	{
		 pService.deleteById(id);
	}
	
	
}
