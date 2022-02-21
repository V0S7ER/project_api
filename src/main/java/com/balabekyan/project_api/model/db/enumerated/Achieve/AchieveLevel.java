package com.balabekyan.project_api.model.db.enumerated.Achieve;

public enum AchieveLevel {
    INTERNATIONAL,
    ALL_RUSSIAN,
    REGIONAL,
    MUNICIPAL,
    SCHOOL;

    public int getIndex() {
        if(this == INTERNATIONAL) {
            return 0;
        } else if(this == ALL_RUSSIAN) {
            return 1;
        } else if(this == REGIONAL) {
            return 2;
        } else if(this == MUNICIPAL) {
            return 3;
        }
        return 4;
    }
}
