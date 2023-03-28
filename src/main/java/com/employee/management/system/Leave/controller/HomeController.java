package com.employee.management.system.Leave.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.employee.management.system.Leave.model.Leaves;
import com.employee.management.system.Leave.service.ServiceLayer;


@RestController
@RequestMapping(path="/leave")
@CrossOrigin(origins = "*")
public class HomeController {
	@Autowired
	ServiceLayer service;
	
	@GetMapping(path = "/all/{email}")
	public List<Leaves> getLeavesByEmail(@PathVariable String email) {
		return service.getLeavesByEmail(email);
	}
	
	@GetMapping(path = "/id/{leaveId}")
	public Optional<Leaves> getLeavesByEmail(@PathVariable int leaveId) {
		return service.getLeavesByLeaveId(leaveId);
	}
	
	@PostMapping(path = "/add")
	public @ResponseBody ResponseEntity<Leaves> addLeave(@RequestBody Leaves leave ){
		return service.addLeave(leave);
	}
	
	@DeleteMapping(path = "/delete/{leaveId}")
	public ResponseEntity<Leaves> DeleteLeaveByLeaveId(@PathVariable int leaveId){
		return service.deleteLeaveById(leaveId);
	}
	
	@PutMapping(path = "/update")
	public ResponseEntity<Leaves> updateLeave(@RequestBody Leaves leave ) {
		return service.updateLeave(leave);
	}
	
	
	@GetMapping(path = "/manager/{managerEmail}")
	public List<Leaves> getLeavesByManagerEmail(@PathVariable String managerEmail) {
		return service.findLeavesByManager(managerEmail);
	}
	
	
	
	
}
