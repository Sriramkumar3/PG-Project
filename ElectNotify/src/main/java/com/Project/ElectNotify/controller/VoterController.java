package com.Project.ElectNotify.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.Project.ElectNotify.Entity.Voter;
import com.Project.ElectNotify.service.VoterService;

@Controller
public class VoterController {
	
	@Autowired
	private VoterService service;
	
	@PostMapping("/add")
    public Voter addVoter(@RequestBody Voter voter) {
        return service.addVoter(voter);
    }

    @GetMapping("/all")
    public List<Voter> getAll() {
        return service.getAllVoters();
    }

    @PutMapping("/vote/{id}")
    public String vote(@PathVariable Long id) {
        return service.updateVoteStatus(id);
    }
}
