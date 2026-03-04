package com.Project.ElectNotify.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Project.ElectNotify.entity.Booth;
import com.Project.ElectNotify.entity.Voter;
import com.Project.ElectNotify.repository.VoterRepository;

@Service
public class VoterService {

    @Autowired
    private VoterRepository voterRepository;

    public List<Voter> getAllVoters() {
        return voterRepository.findAll();
    }

    public void saveVoter(Voter voter) {
        voterRepository.save(voter);
    }
    
    public List<Voter> getVotersByBooth(Booth booth) {
        return voterRepository.findByBooth(booth);
    }

    public Voter getVoterById(int sno) {
        return voterRepository.findById(sno).orElse(null);
    }

    public void deleteVoter(int sno) {
        voterRepository.deleteById(sno);
    }
    
    public long getTotalVoters(Booth booth) {
        return voterRepository.countByBooth(booth);
    }

    public long getTotalVoted(Booth booth) {
        return voterRepository.countByBoothAndVoted(booth, true);
    }
    
    public List<Voter> getNonVotersByBooth(Booth booth) {
        return voterRepository.findByBoothAndVotedFalse(booth);
    }
    
    public List<Voter> getNonVoters(){
        return voterRepository.findByVotedFalse();
    }
}
