package com.Project.ElectNotify.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Project.ElectNotify.Entity.Booth;
import com.Project.ElectNotify.Entity.Voter;
import com.Project.ElectNotify.repository.BoothRepository;
import com.Project.ElectNotify.repository.VoterRepository;

@Service
public class AdminDashboardService {

    @Autowired
    private VoterRepository voterrepo;
    @Autowired
    private BoothRepository boothrepo;

    public Map<String, Object> getDashboardData() {

        long totalVoters = voterrepo.count();
        long voted = voterrepo.countByVotedTrue();
        long notVoted = voterrepo.countByVotedFalse();

        double percentage = 0;
        if (totalVoters > 0) {
            percentage = ((double) voted / totalVoters) * 100;
        }

        List<Object[]> boothData = voterrepo.countVotersGroupByBooth();

        Map<String, Object> response = new HashMap<>();
        response.put("Total Voters", totalVoters);
        response.put("Voted", voted);
        response.put("Not Voted", notVoted);
        response.put("Voting Percentage", percentage);
        response.put("Booth Wise Data", boothData);
        
        List<Booth> boothList = boothrepo.findAll();
        response.put("boothList", boothList);

        return response;
    }
    
    public Map<String, Object> getBoothDetails(int boothId) {

        Booth booth = boothrepo.findById(boothId)
                .orElseThrow(() -> new RuntimeException("Booth not found"));

        List<Voter> voters = voterrepo.findByBooth_BoothId(boothId);

        long total = voterrepo.countByBooth_BoothId(boothId);
        long voted = voterrepo.countByBooth_BoothIdAndVotedTrue(boothId);
        long notVoted = total - voted;

        double percentage = 0;
        if (total > 0) {
            percentage = ((double) voted / total) * 100;
        }

        Map<String, Object> data = new HashMap<>();
        data.put("booth", booth);
        data.put("voters", voters);
        data.put("total", total);
        data.put("voted", voted);
        data.put("notVoted", notVoted);
        data.put("percentage", percentage);

        return data;
    }
}
