package com.balabekyan.project_api.model.db.enumerated.Achieve;

public enum AchieveType {
    OLIMPIAD_VSOSH,
    OLIMPIAD_PERECHEN,
    SPORT,
    OTHER;

    public int getIndex() {
        if (this == OLIMPIAD_VSOSH) {
            return 0;
        } else if (this == OLIMPIAD_PERECHEN) {
            return 1;
        } else if (this == SPORT) {
            return 2;
        }
        return 3;
    }
}
