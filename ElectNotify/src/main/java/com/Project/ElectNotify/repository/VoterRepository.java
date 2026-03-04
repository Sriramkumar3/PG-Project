package com.Project.ElectNotify.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Project.ElectNotify.entity.Booth;
import com.Project.ElectNotify.entity.Voter;

public interface VoterRepository extends JpaRepository<Voter, Integer> {
	List<Voter> findByBooth(Booth booth);
	
	long countByBooth(Booth booth);

	long countByBoothAndVoted(Booth booth, boolean voted);
	
	List<Voter> findByVotedFalse();
	List<Voter> findByBoothAndVotedFalse(Booth booth);

	int countByBoothBoothId(int boothId);
	
    long countByBoothBoothIdAndVotedTrue(int boothId);
}