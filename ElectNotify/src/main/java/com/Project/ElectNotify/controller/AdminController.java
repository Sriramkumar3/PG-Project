package com.Project.ElectNotify.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Project.ElectNotify.service.AdminDashboardService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminDashboardService dashboardService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        Map<String, Object> data = dashboardService.getDashboardData();

        model.addAttribute("totalVoters", data.get("Total Voters"));
        model.addAttribute("voted", data.get("Voted"));
        model.addAttribute("notVoted", data.get("Not Voted"));
        model.addAttribute("percentage", data.get("Voting Percentage"));
        model.addAttribute("boothData", data.get("Booth Wise Data"));

        
        model.addAttribute("boothList", data.get("boothList"));

        return "dashboard";
    }
    
    @GetMapping("/booth/{boothId}")
    public String boothDetails(@PathVariable int boothId, Model model) {

        Map<String, Object> data = dashboardService.getBoothDetails(boothId);

        model.addAttribute("booth", data.get("booth"));
        model.addAttribute("voters", data.get("voters"));
        model.addAttribute("total", data.get("total"));
        model.addAttribute("voted", data.get("voted"));
        model.addAttribute("notVoted", data.get("notVoted"));
        model.addAttribute("percentage", data.get("percentage"));

        return "booth-details";
    }
}