package com.balabekyan.project_api.service;

import com.balabekyan.project_api.model.db.enumerated.Achieve.AchieveLevel;
import com.balabekyan.project_api.model.db.enumerated.Achieve.AchievePlace;
import com.balabekyan.project_api.model.db.enumerated.Achieve.AchieveType;

public class WeightSystem {
    static int getWeight(AchieveType type, AchieveLevel level, AchievePlace place) {
        int[] typeDelta = new int[] {10, 5, 5, 2};
        int[] levelDelta = new int[] {30, 15, 10, 7, 3};
        int[] placeDelta = new int[] {10, 5, 1};

        int typeIndex = type.getIndex(), levelIndex = level.getIndex(), placeIndex = place.getIndex();
        return typeDelta[typeIndex] * levelDelta[levelIndex] * placeDelta[placeIndex];
    }
}
