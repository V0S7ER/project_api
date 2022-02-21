package com.balabekyan.project_api.model.dto.request;

import lombok.Data;

@Data
public class AchieveRequest {
    private Integer weight;

    private String type;

    private String level;

    private String status;

    private String place;

    private String description;

    //Пользовательские данные
    private Long user_id;
    private String user_password;
}