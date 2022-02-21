package com.balabekyan.project_api.controller;

import com.balabekyan.project_api.model.db.Achieve;
import com.balabekyan.project_api.model.dto.request.AchieveRequest;
import com.balabekyan.project_api.model.dto.response.StatusResponse;
import com.balabekyan.project_api.service.AchieveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/achieve")
@RequiredArgsConstructor
public class AchieveController {

    private final AchieveService achieveService;

    /**
     * Получить ачивку по айди
     * Добавить ачивку
     * Редактировать ачивку
     * Удалить ачивку
    */

    @GetMapping("/{id}")
    private synchronized ResponseEntity<?> getById(@PathVariable("id") Long id) {
        Achieve achieve = achieveService.get(id);
        return achieve == null ? new ResponseEntity(null, HttpStatus.BAD_REQUEST) : ResponseEntity.ok(achieve);
    }

    @PostMapping("/add")
    private synchronized ResponseEntity<?> add(AchieveRequest request) {
        StatusResponse response = achieveService.add(request);
        return response.isOK() ? ResponseEntity.ok(response) : new ResponseEntity(null, HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    private synchronized ResponseEntity<?> edit(@PathVariable("id") Long id, AchieveRequest request) {
        StatusResponse response = achieveService.edit(id, request);
        return response.isOK() ? ResponseEntity.ok(response) : new ResponseEntity(null, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    private synchronized ResponseEntity<?> delete(@PathVariable("id") Long id, Long user_id, String password) {
        StatusResponse response = achieveService.delete(id, user_id, password);
        return response.isOK() ? ResponseEntity.ok(response) : new ResponseEntity(null, HttpStatus.BAD_REQUEST);
    }
}
