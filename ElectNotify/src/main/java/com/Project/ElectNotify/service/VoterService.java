package com.Project.ElectNotify.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Project.ElectNotify.Entity.Voter;
import com.Project.ElectNotify.repository.VoterRepository;

@Service
public class VoterService {
	@Autowired
	private VoterRepository repo;
	
	
	 public Voter addVoter(Voter voter) {
	        return repo.save(voter);
	    }

	    public List<Voter> getAllVoters() {
	        return repo.findAll();
	    }

	    public String updateVoteStatus(Long id) {
	        Voter voter = repo.findById(id).orElseThrow();
	        voter.setVoted(true);
	        repo.save(voter);
	        return "Vote Updated Successfully";
	    }
}
