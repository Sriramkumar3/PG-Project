package com.Project.ElectNotify.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.Project.ElectNotify.dto.BoothStatsDTO;
import com.Project.ElectNotify.entity.Booth;
import com.Project.ElectNotify.entity.User;
import com.Project.ElectNotify.entity.Voter;
import com.Project.ElectNotify.repository.BoothRepository;
import com.Project.ElectNotify.repository.UserRepository;
import com.Project.ElectNotify.repository.VoterRepository;
import com.Project.ElectNotify.service.BoothService;
import com.Project.ElectNotify.service.EmailService;
import com.Project.ElectNotify.service.VoterService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	
    @Autowired
    private EmailService emailService;
    
    @Autowired
    private BoothService boothService;
    
    @Autowired
    private VoterService voterService;
    
    @Autowired
    private BoothRepository boothRepository;
    
    @GetMapping("/booth-stats")
    public String boothStats(Model model) {

        List<Booth> booths = boothService.getAllBooths();
        List<BoothStatsDTO> statsList = new ArrayList<>();

        for (Booth booth : booths) {

            long total = voterService.getTotalVoters(booth);
            long voted = voterService.getTotalVoted(booth);

            statsList.add(new BoothStatsDTO(
                    booth.getVillage(),
                    total,
                    voted
            ));
        }

        model.addAttribute("statsList", statsList);

        return "admin-booth-stats";
    }
    
    @GetMapping("/booth")
    public String viewBooths(Model model) {
        model.addAttribute("booths", boothService.getAllBooths());
        return "booth-list";
    }

    @GetMapping("/booth/add")
    public String addBoothForm(Model model) {
        model.addAttribute("booth", new Booth());
        return "booth-form";
    }

    @PostMapping("/booth/save")
    public String saveBooth(@ModelAttribute Booth booth) {
        boothService.saveBooth(booth);
        return "redirect:/admin/booth";
    }

    @GetMapping("/booth/delete/{id}")
    public String deleteBooth(@PathVariable Integer id) {
        boothService.deleteBooth(id);
        return "redirect:/admin/booth";
    }
    
    

    @Autowired
    private VoterRepository voterRepository;
    
    @GetMapping("/dashboard")
    public String adminDashboard(Model model){

        List<Booth> booths = boothRepository.findAll();

        model.addAttribute("booths", booths);

        List<String> boothNames = new ArrayList<>();
        List<Integer> boothPercentages = new ArrayList<>();

        for(Booth booth : booths){

            List<Voter> voters = voterRepository.findByBooth(booth);

            int total = voters.size();

            long voted = voters.stream()
                    .filter(v -> v.isVoted())
                    .count();

            int percent = 0;

            if(total > 0){
                percent = (int)((voted * 100) / total);
            }

            boothNames.add(booth.getVillage());
            boothPercentages.add(percent);
        }

        model.addAttribute("boothNames", boothNames);
        model.addAttribute("boothPercentages", boothPercentages);

        return "admin-dashboard";
    }
    
    
    @GetMapping("/sendNotification")
    public String sendNotification() {

        List<Voter> nonVoters = voterService.getNonVoters();

        for(Voter v : nonVoters){

            if(v.getEmail() != null){

                emailService.sendMail(
                        v.getEmail(),
                        "Election Reminder",
                        "Dear " + v.getVoterName() +
                        ", please cast your vote today."
                );

            }
        }

        return "redirect:/admin/dashboard";
    }
    
    @GetMapping("/booth-comparison")
    public String boothComparison(Model model) {

        List<Booth> booths = boothService.getAllBooths();

        Map<Integer, Double> boothPercentages = new HashMap<>();

        for (Booth booth : booths) {

            double percentage = boothService.getBoothVotingPercentage(booth.getBoothId());

            boothPercentages.put(booth.getBoothId(), percentage);
        }

        model.addAttribute("booths", booths);
        model.addAttribute("boothPercentages", boothPercentages);

        return "admin-booth-comparison";
    }
    
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @GetMapping("/add-user")
    public String addUserPage(Model model) {

        model.addAttribute("user", new User());
        model.addAttribute("booths", boothService.getAllBooths());

        return "add-user";
    }

    @PostMapping("/save-user")
    public String saveUser(@ModelAttribute User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return "redirect:/admin/dashboard";
    }
}