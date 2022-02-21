package com.balabekyan.project_api.repository;

import com.balabekyan.project_api.model.db.Achieve;
import com.balabekyan.project_api.model.db.enumerated.Achieve.AchieveStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AchieveRepository extends JpaRepository<Achieve, Long> {
    List<Achieve> findByStatusIs(AchieveStatus status);


}
