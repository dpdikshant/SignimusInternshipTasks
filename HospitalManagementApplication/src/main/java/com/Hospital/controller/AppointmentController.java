package com.Hospital.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Hospital.model.Appointment;
import com.Hospital.services.AppointmentService;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {
	@Autowired
	private AppointmentService aService;
	
	@GetMapping("/appointList")
	public List<Appointment> getAppointmentList()
	{
		List<Appointment> AppointList = aService.getAllDoctors();
		return AppointList;
		
	}
	
	@PostMapping("/saveAppointment")
	public Appointment makeAppointment(@RequestBody Appointment appointment)
	{
		Appointment appoint = aService.save(appointment);
		return appoint;
	}
	
    @GetMapping("/checkAppoinmentId/{id}")
	public Appointment getAppoinmentById(@PathVariable int id)
	{
		Appointment appoint = aService.findById(id);
		return appoint;
	}
	
    @GetMapping("/delAppointmentId/{id}")
	public void delAppoitmentById(@PathVariable int id)
	{
		aService.deleteById(id);
	}

}
