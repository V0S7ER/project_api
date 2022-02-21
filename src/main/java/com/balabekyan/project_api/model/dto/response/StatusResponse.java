package com.balabekyan.project_api.model.dto.response;

import lombok.Data;

@Data
public class StatusResponse {
    private String status = "OK";

    public boolean isOK() {
        return status.equals("OK");
    }
}
