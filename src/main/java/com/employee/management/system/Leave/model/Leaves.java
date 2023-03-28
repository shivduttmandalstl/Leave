package com.employee.management.system.Leave.model;


import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "Leaves")
public class Leaves {
	
	@Id
	@GeneratedValue(generator = "leave_gen",strategy = GenerationType.AUTO)
	@SequenceGenerator(name="leave_gen",sequenceName = "leave_seq",initialValue = 3000,allocationSize = 1)
	@Column(name="leaveId")
	private int leaveId;
	
	@Column(name="email",nullable = false)
	private String email;
	
	@JsonFormat(pattern="yyyy-MM-dd")
	@Column(name="fromDate",nullable = false)
	private LocalDate fromDate;
	
	@JsonFormat(pattern="yyyy-MM-dd")
	@Column(name="toDate",nullable = false)
	private LocalDate toDate;
	
	@Column(name="leaveType",nullable = false)
	private String leaveType;
	
	@Column(name="reason",nullable = false)
	private String reason;
	
	
	@Column(name = "managerName")
	private String managerName;	

	@Column(name = "managerEmail")
	private String managerEmail;
	
	@Column(name="status")
	private String status= "Pending";
	
	@Column(name="duration")
	private int duration;

	public int getLeaveId() {
		return leaveId;
	}

	public void setLeaveId(int leaveId) {
		this.leaveId = leaveId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getFromDate() {
		return fromDate;
	}

	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}

	public LocalDate getToDate() {
		return toDate;
	}

	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getManagerEmail() {
		return managerEmail;
	}

	public void setManagerEmail(String managerEmail) {
		this.managerEmail = managerEmail;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration ;
	}

	public String getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	
	
	
}
