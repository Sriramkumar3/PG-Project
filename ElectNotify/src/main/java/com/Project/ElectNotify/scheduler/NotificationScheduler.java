package com.Project.ElectNotify.scheduler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.Project.ElectNotify.entity.Voter;
import com.Project.ElectNotify.service.EmailService;
import com.Project.ElectNotify.service.VoterService;

@Component
public class NotificationScheduler {

    @Autowired
    private VoterService voterService;

    @Autowired
    private EmailService emailService;

    // every 2 hours
   // @Scheduled(cron = "0 0 */2 * * *")
    
    // testing 
    //@Scheduled(cron = "0 */1 * * * *")
    public void sendReminderToNonVoters() {

        List<Voter> nonVoters = voterService.getNonVoters();

        for (Voter voter : nonVoters) {

            if (voter.getEmail() != null) {

                emailService.sendMail(
                        voter.getEmail(),
                        "Election Reminder",
                        "Dear " + voter.getVoterName()
                                + ", You have not voted yet. Please cast your vote today."
                );
            }
        }

        System.out.println("Reminder emails sent to non-voters.");
    }
}
