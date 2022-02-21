package com.balabekyan.project_api.model.db;

import com.balabekyan.project_api.model.db.enumerated.Achieve.AchieveLevel;
import com.balabekyan.project_api.model.db.enumerated.Achieve.AchievePlace;
import com.balabekyan.project_api.model.db.enumerated.Achieve.AchieveStatus;
import com.balabekyan.project_api.model.db.enumerated.Achieve.AchieveType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "achieves")
public class Achieve {

    public Achieve() {

    }

    public Achieve(int weight, AchieveType type, AchieveLevel level, AchieveStatus status, AchievePlace place, String description, User user) {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer weight;

    @Enumerated(EnumType.STRING)
    private AchieveType type;

    @Enumerated(EnumType.STRING)
    private AchieveLevel level;

    @Enumerated(EnumType.STRING)
    private AchieveStatus status;

    @Enumerated(EnumType.STRING)
    private AchievePlace place;
    private String description;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;
}