package com.employee.management.system.Leave.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.employee.management.system.Leave.model.Leaves;
import com.employee.management.system.Leave.repository.LeaveRepository;


@Service
public class ServiceLayer {
	
	@Autowired
	LeaveRepository repository;
	
	public List<Leaves> getLeavesByEmail(String email) {
		repository.updateManagerNameByEmail(email);
		repository.updateManagerEmailByEmail(email);
		if(repository.findByEmail(email).isEmpty()) {
			return null;
		}
		else {
			
			return repository.findByEmail(email);
		}
	}
	
	public ResponseEntity<Leaves> addLeave(Leaves leave ){
		List<Object> dataFrom = repository.getFDates(leave.getEmail(), leave.getFromDate(), leave.getToDate());
		List<Object> dataTo = repository.getTDates(leave.getEmail(), leave.getFromDate(), leave.getToDate());
		if(leave.getToDate().isBefore(leave.getFromDate())|| leave.getFromDate().isBefore(LocalDate.now())) {
			return new ResponseEntity<Leaves>(HttpStatus.NOT_ACCEPTABLE);			
		}
		else if (dataFrom.isEmpty() && dataTo.isEmpty()) {
			Period difference = Period.between(leave.getFromDate(), leave.getToDate());
			leave.setDuration(difference.getDays()+1);
			leave.setManagerName(repository.getManagerName(leave.getEmail()));
			leave.setManagerEmail(repository.getManagerEmail(leave.getEmail()));
			return new ResponseEntity<Leaves>(repository.save(leave),HttpStatus.CREATED);	
		}
		else {
			return new ResponseEntity<Leaves>(HttpStatus.ALREADY_REPORTED);
		}
	}
	
	public ResponseEntity<Leaves> deleteLeaveById(int leaveId){
		if(repository.existsById(leaveId)) {
			repository.deleteById(leaveId);
			return new ResponseEntity<Leaves>(HttpStatus.OK);
		}
		else {
			return new ResponseEntity<Leaves>(HttpStatus.NOT_FOUND);
		}
	}
	
	public ResponseEntity<Leaves> updateLeave(Leaves leave ) {
		Period difference = Period.between(leave.getFromDate(), leave.getToDate());
		leave.setDuration(difference.getDays()+1);
		leave.setManagerName(repository.getManagerName(leave.getEmail()));
		leave.setManagerEmail(repository.getManagerEmail(leave.getEmail()));
		return new ResponseEntity<Leaves>(repository.save(leave),HttpStatus.OK);
	}

	public Optional<Leaves> getLeavesByLeaveId(int leaveId) {
		repository.updateManagerNameByEmailAndId(repository.getEmailByLeaveId(leaveId), leaveId);
		repository.updateManagerEmailByEmailAndId(repository.getEmailByLeaveId(leaveId), leaveId);
		return repository.findById(leaveId);
	}
	
	public List<Leaves> findLeavesByManager(String managerEmail){
		return repository.findByManagerEmail(managerEmail);
	}
}
