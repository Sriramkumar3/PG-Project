package com.Project.ElectNotify.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Project.ElectNotify.entity.Booth;
import com.Project.ElectNotify.entity.User;
import com.Project.ElectNotify.repository.BoothRepository;
import com.Project.ElectNotify.repository.UserRepository;
import com.Project.ElectNotify.repository.VoterRepository;

@Service
public class BoothService {

    @Autowired
    private BoothRepository boothRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private VoterRepository voterRepository;

    // Get All Booths
    public List<Booth> getAllBooths() {
        return boothRepository.findAll();
    }

    // Get Booth By Id
    public Booth getBoothById(Integer id) {
        return boothRepository.findById(id).orElse(null);
    }

    // Save Booth
    public Booth saveBooth(Booth booth) {
        return boothRepository.save(booth);
    }

    // Delete Booth
    public void deleteBooth(Integer id) {
        boothRepository.deleteById(id);
    }
    
    public Booth getBoothByBLO(String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return user.getBooth();
    }
    
    public double getBoothVotingPercentage(int boothId) {

        int total = voterRepository.countByBoothBoothId(boothId);
        long voted = voterRepository.countByBoothBoothIdAndVotedTrue(boothId);

        if (total == 0) {
            return 0;
        }

        return (voted * 100.0) / total;
    }
}
