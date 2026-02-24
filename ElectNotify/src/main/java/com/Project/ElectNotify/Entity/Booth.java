package com.Project.ElectNotify.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Booth {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int boothId;
	private String Village;
	private String city;
	private int totalVoter;
	private String constituency;
	private String bloName;
	private String bloNumber;
	public int getBoothId() {
		return boothId;
	}
	public void setBoothId(int boothId) {
		this.boothId = boothId;
	}
	public String getVillage() {
		return Village;
	}
	public void setVillage(String village) {
		Village = village;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public int getTotalVoter() {
		return totalVoter;
	}
	public void setTotalVoter(int totalVoter) {
		this.totalVoter = totalVoter;
	}
	public String getConstituency() {
		return constituency;
	}
	public void setConstituency(String constituency) {
		this.constituency = constituency;
	}
	public String getBloName() {
		return bloName;
	}
	public void setBloName(String bloName) {
		this.bloName = bloName;
	}
	public String getBloNumber() {
		return bloNumber;
	}
	public void setBloNumber(String bloNumber) {
		this.bloNumber = bloNumber;
	}
	
	
}
