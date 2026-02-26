package com.Project.ElectNotify.Entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Voter {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int sno;
	private String voterName;
	private String aadhaarNo;
	private Long voterId;
	private LocalDate dob;
	private String email;
	private String phoneNumber;
	private boolean voted = false;
	@JoinColumn(name = "boothId")
	@ManyToOne
	private Booth booth;
	public int getSno() {
		return sno;
	}
	public void setSno(int sno) {
		this.sno = sno;
	}
	public String getVoterName() {
		return voterName;
	}
	public void setVoterName(String voterName) {
		this.voterName = voterName;
	}
	public String getAadhaarNo() {
		return aadhaarNo;
	}
	public void setAadhaarNo(String aadhaarNo) {
		this.aadhaarNo = aadhaarNo;
	}
	public Long getVoterId() {
		return voterId;
	}
	public void setVoterId(Long voterId) {
		this.voterId = voterId;
	}
	public LocalDate getDob() {
		return dob;
	}
	public void setDob(LocalDate dob) {
		this.dob = dob;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public boolean isVoted() {
		return voted;
	}
	public void setVoted(boolean voted) {
		this.voted = voted;
	}
	public Booth getBooth() {
		return booth;
	}
	public void setBooth(Booth booth) {
		this.booth = booth;
	}
	
	
}
