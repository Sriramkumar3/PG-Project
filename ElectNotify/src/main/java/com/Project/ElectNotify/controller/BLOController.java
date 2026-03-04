package com.Project.ElectNotify.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.Project.ElectNotify.entity.Booth;
import com.Project.ElectNotify.entity.User;
import com.Project.ElectNotify.entity.Voter;
import com.Project.ElectNotify.repository.BoothRepository;
import com.Project.ElectNotify.repository.UserRepository;
import com.Project.ElectNotify.repository.VoterRepository;
import com.Project.ElectNotify.service.BoothService;
import com.Project.ElectNotify.service.VoterService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/blo")
public class BLOController {

    @Autowired
    private BoothService boothService;
    
    @Autowired
    private VoterService voterService;
    
    @Autowired
    private UserRepository userRepository;
    
    @GetMapping("/voters")
    public String viewBloVoters(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        User user = userRepository.findByUsername(username).get();

        Booth booth = user.getBooth();

        model.addAttribute("voters", voterService.getVotersByBooth(booth));

        return "blo-voter-list";
    }
    
    @GetMapping("/markVoted/{sno}")
    public String markAsVoted(@PathVariable Integer sno) {

        Voter voter = voterService.getVoterById(sno);

        if (voter != null) {
            voter.setVoted(true);
            voterService.saveVoter(voter);
        }

        return "redirect:/blo/voters";
    }
    
    
    @GetMapping("/dashboard")
    public String bloDashboard(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        User user = userRepository.findByUsername(username).get();
        Booth booth = user.getBooth();

        long total = voterService.getTotalVoters(booth);
        long voted = voterService.getTotalVoted(booth);
        long remaining = total - voted;

        double percentage = 0;
        if (total > 0) {
            percentage = ((double) voted / total) * 100;
        }

        model.addAttribute("total", total);
        model.addAttribute("voted", voted);
        model.addAttribute("remaining", remaining);
        model.addAttribute("percentage", Math.round(percentage));
        
        //checking
        System.out.println("Booth ID : " + booth.getBoothId());
        System.out.println("Voters : " + voterService.getVotersByBooth(booth).size());

        return "blo-dashboard";
    }
    
    @GetMapping("/non-voters")
    public String viewNonVoters(Model model, Authentication authentication) {

        String username = authentication.getName();
        Booth booth = boothService.getBoothByBLO(username);

        List<Voter> nonVoters = voterService.getNonVotersByBooth(booth);

        model.addAttribute("nonVoters", nonVoters);

        return "blo-non-voters";
    }
    
    @PostMapping("/blo/update-voted/{voterId}")
    public String updateVotedStatus(@PathVariable int voterId,
                                    Principal principal) {

        String username = principal.getName();

        Booth booth = boothService.getBoothByBLO(username);

        Voter voter = voterService.getVoterById(voterId);

        if (voter.getBooth().getBoothId() != booth.getBoothId()) {
            throw new RuntimeException("Unauthorized access");
        }

        voter.setVoted(true);
        voterService.saveVoter(voter);

        return "redirect:/blo/dashboard";
    }
}