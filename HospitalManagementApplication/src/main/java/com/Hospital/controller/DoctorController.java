package com.Hospital.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Hospital.model.Doctor;
import com.Hospital.services.DoctorServices;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

	@Autowired
	private DoctorServices dService;
	
	@GetMapping("/docList")
	public List<Doctor> getAllDoctor()
	{
		List<Doctor> docList = dService.getAllDoctors();
		return docList;
	}
	
	@PostMapping("/saveDoc")
	public Doctor saveDoctor(@RequestBody Doctor doctor)
	{
		Doctor doc = dService.save(doctor);
		return doc;
	}
	
	@GetMapping("/findDoc{id}")
	public Doctor docByID(@PathVariable int id)
	{
		Doctor doc = dService.findById(id);
		return doc;
	}
	
	@GetMapping("/delDoc{id}")
	public void delDocById(@PathVariable int id)
	{
		dService.deleteById(id);
		
	}
	
	
}
