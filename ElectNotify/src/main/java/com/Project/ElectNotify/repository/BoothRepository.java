package com.Project.ElectNotify.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Project.ElectNotify.entity.Booth;

public interface BoothRepository extends JpaRepository<Booth, Integer> {
	Booth findByBoothIdAndBloNumber(int boothId, String bloNumber);

}
