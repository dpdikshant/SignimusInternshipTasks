package com.Hospital.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.Hospital.model.Patient;
import com.Hospital.repository.PatientRepository;

@Service
public class PatientServices {

	@Autowired
	private PatientRepository pRepo;
	
	public List<Patient> getAllPatient()
	{
		List<Patient> patientList = pRepo.findAll();
		return patientList;
	}
	
	public Patient savePatient(Patient patient) 
	{
		return pRepo.save(patient);
		
	}
	
	  public Patient findById(int id)
	    {
	        Patient patientCheck =null;
	        Optional<Patient> patient = pRepo.findById(id);
	        if(patient.isPresent())
	        {
	            patientCheck = patient.get();
	        }
	        return patientCheck;
	    }
	  
	  public void deleteById(int id)
	  {
		pRepo.deleteById(id); 
	  }


}
