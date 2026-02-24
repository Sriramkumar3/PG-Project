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
	public String getVoter_name() {
		return voterName;
	}
	public void setVoter_name(String voter_name) {
		this.voterName = voter_name;
	}
	public String getAadhar_no() {
		return aadhaarNo;
	}
	public void setAadhar_no(String aadhar_no) {
		this.aadhaarNo = aadhar_no;
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
	public String getPhone_number() {
		return phoneNumber;
	}
	public void setPhone_number(String phone_number) {
		this.phoneNumber = phone_number;
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
	public Long getVoter_id() {
		return voterId;
	}
	public void setVoter_id(Long voter_id) {
		this.voterId = voter_id;
	}
	
	
	
}
