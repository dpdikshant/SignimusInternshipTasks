package com.Hospital.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Hospital.model.Appointment;
import com.Hospital.model.Doctor;
import com.Hospital.repository.AppointmentRepository;
import com.Hospital.repository.DoctorRepository;
@Service
public class AppointmentService {
	
	@Autowired
	private AppointmentRepository aRepo;
	
	 public List<Appointment> getAllDoctors()
	    {
	        List<Appointment> appointmentList = aRepo.findAll();
	        return appointmentList;
	    }

	    public Appointment save(Appointment appointment)
	    {
	        return aRepo.save(appointment);
	    }

	    public Appointment findById(int id)
	    {
	        Appointment appointmentCheck =null;
	        Optional<Appointment> patient = aRepo.findById(id);
	        if(patient.isPresent())
	        {
	            appointmentCheck = patient.get();
	        }
	        return appointmentCheck;
	    }


	    public void deleteById(int id)
	    {
	        aRepo.deleteById(id);
	    }
	

}
