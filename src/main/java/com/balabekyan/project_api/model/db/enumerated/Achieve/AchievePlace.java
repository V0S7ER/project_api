package com.balabekyan.project_api.model.db.enumerated.Achieve;

public enum AchievePlace {
    WINNER,
    PRIZE,
    PARTICIPANT;

    public int getIndex() {
        if(this == WINNER) {
            return 0;
        } else if(this == PRIZE) {
            return 1;
        }
        return 2;
    }
}
