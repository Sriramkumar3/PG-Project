package com.Project.ElectNotify.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.Project.ElectNotify.entity.Voter;
import com.Project.ElectNotify.repository.BoothRepository;
import com.Project.ElectNotify.service.VoterService;

@Controller
@RequestMapping("/admin/voters")
public class AdminVoterController {

    @Autowired
    private VoterService voterService;
    
    @Autowired
    private BoothRepository boothRepository;

    // List all voters
    @GetMapping("/allvoters")
    public String listVoters(Model model) {
        model.addAttribute("voters", voterService.getAllVoters());
        return "voter-list";
    }

    // Show add form
    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("voter", new Voter());
        model.addAttribute("booths", boothRepository.findAll());
        return "voter-form";
    }

    // Save voter
    @PostMapping("/save")
    public String saveVoter(@ModelAttribute Voter voter) {
        voterService.saveVoter(voter);
        return "redirect:/admin/voters";
    }

    // Edit voter
    @GetMapping("/edit/{sno}")
    public String editVoter(@PathVariable Integer sno, Model model) {
        model.addAttribute("voter", voterService.getVoterById(sno));
        model.addAttribute("booths", boothRepository.findAll());
        return "voter-form";
    }

    // Delete voter
    @GetMapping("/delete/{sno}")
    public String deleteVoter(@PathVariable Integer sno) {
        voterService.deleteVoter(sno);
        return "redirect:/admin/voters";
    }
}