package com.Project.ElectNotify.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.Project.ElectNotify.Entity.Booth;
import com.Project.ElectNotify.Entity.Voter;
import com.Project.ElectNotify.repository.BoothRepository;
import com.Project.ElectNotify.repository.VoterRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/blo")
public class BLOController {

    @Autowired
    private BoothRepository boothRepository;
    
    @Autowired
    private VoterRepository voterRepository;

    @GetMapping("/login")
    public String showLoginPage() {
        return "blo-login";
    }

    @PostMapping("/login")
    public String login(@RequestParam int boothId,
                        @RequestParam String bloNumber,
                        HttpSession session,
                        Model model) {

        Booth booth = boothRepository.findByBoothIdAndBloNumber(boothId, bloNumber);

        if (booth != null) {
            session.setAttribute("loggedBooth", booth);
            return "redirect:/blo/dashboard";
        } else {
            model.addAttribute("error", "Invalid Credentials");
            return "blo-login";
        }
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {

        Booth booth = (Booth) session.getAttribute("loggedBooth");

        if (booth == null) {
            return "redirect:/blo/login";
        }

        List<Voter> voters = voterRepository.findByBooth_BoothId(booth.getBoothId());

        model.addAttribute("booth", booth);
        model.addAttribute("voters", voters);

        return "blo-dashboard";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/blo/login";
    }
    
    @PostMapping("/updateVote/{voterId}")
    public String updateVote(@PathVariable Long voterId,
                             HttpSession session) {

        Booth booth = (Booth) session.getAttribute("loggedBooth");

        if (booth == null) {
            return "redirect:/blo/login";
        }

        Optional<Voter> optionalVoter =
                voterRepository.findByVoterIdAndBooth_BoothId(voterId, booth.getBoothId());

        if (optionalVoter.isPresent()) {
            Voter voter = optionalVoter.get();
            voter.setVoted(true);
            voterRepository.save(voter);
        }

        return "redirect:/blo/dashboard";
    }
    
    @GetMapping("/liveStats")
    @ResponseBody
    public Map<String, Object> getLiveStats(HttpSession session) {

        Booth booth = (Booth) session.getAttribute("loggedBooth");

        if (booth == null) {
            return Map.of("error", "Not Logged In");
        }

        int boothId = booth.getBoothId();

        long total = voterRepository.countByBooth_BoothId(boothId);
        long voted = voterRepository.countByBooth_BoothIdAndVotedTrue(boothId);
        long notVoted = total - voted;

        double percentage = 0;
        if (total > 0) {
            percentage = ((double) voted / total) * 100;
        }

        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("voted", voted);
        data.put("notVoted", notVoted);
        data.put("percentage", percentage);

        return data;
    }
    
    @GetMapping("/votedList")
    @ResponseBody
    public List<Long> getVotedVoters(HttpSession session) {

        Booth booth = (Booth) session.getAttribute("loggedBooth");

        if (booth == null) {
            return List.of();
        }

        return voterRepository
                .findByBooth_BoothId(booth.getBoothId())
                .stream()
                .filter(Voter::isVoted)
                .map(Voter::getVoterId) // use correct id field name
                .toList();
    }
}