package com.Hospital.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Service;

import com.Hospital.model.Doctor;
import com.Hospital.repository.DoctorRepository;

@Service
public class DoctorServices {
	
	@Autowired
	private DoctorRepository dRepo;
	
	 public List<Doctor> getAllDoctors()
	    {
	        List<Doctor> doctorList = dRepo.findAll();
	        return doctorList;
	    }

	    public Doctor save(Doctor doctor)
	    {
	        return dRepo.save(doctor);
	    }

	    public Doctor findById(int id)
	    {
	        Doctor doctorCheck =null;
	        Optional<Doctor> patient = dRepo.findById(id);
	        if(patient.isPresent())
	        {
	            doctorCheck = patient.get();
	        }
	        return doctorCheck;
	    }


	    public void deleteById(int id)
	    {
	        dRepo.deleteById(id);
	    }

}
