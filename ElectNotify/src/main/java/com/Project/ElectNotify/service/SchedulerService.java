package com.Project.ElectNotify.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.Project.ElectNotify.Entity.Voter;
import com.Project.ElectNotify.repository.VoterRepository;

@Service
public class SchedulerService {

    @Autowired
    private VoterRepository voterRepository;

    @Autowired
    private NotificationService notificationService;

    @Scheduled(cron = "0 0 */2 * * *")
    public void sendReminderToNonVoters() {

        List<Voter> voters = voterRepository.findByVotedFalse();

        for (Voter voter : voters) {
            notificationService.sendEmail(voter.getEmail());
        }

        System.out.println("Reminder sent to non-voters.");
    }
}
