package com.Project.ElectNotify.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.Project.ElectNotify.Entity.Voter;

public interface VoterRepository extends JpaRepository<Voter, Long> {
	List<Voter> findByVotedFalse();
	
	long countByVotedTrue();

    long countByVotedFalse();

    @Query("SELECT v.boothId, COUNT(v) FROM Booth v GROUP BY v.boothId")
    List<Object[]> countVotersGroupByBooth();

    List<Voter> findByBooth_BoothId(int boothId);

    long countByBooth_BoothId(int boothId);

    long countByBooth_BoothIdAndVotedTrue(int boothId);
    
    Optional<Voter> findByVoterIdAndBooth_BoothId(Long voterId, int boothId);
}
