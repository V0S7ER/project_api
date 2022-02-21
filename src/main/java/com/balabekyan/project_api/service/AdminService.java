package com.balabekyan.project_api.service;

import com.balabekyan.project_api.model.db.Achieve;
import com.balabekyan.project_api.model.db.User;
import com.balabekyan.project_api.model.db.enumerated.Achieve.AchieveStatus;
import com.balabekyan.project_api.model.db.enumerated.UserType;
import com.balabekyan.project_api.model.dto.response.StatusResponse;
import com.balabekyan.project_api.repository.AchieveRepository;
import com.balabekyan.project_api.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AchieveRepository achieveRepository;
    private final UsersRepository usersRepository;

    public StatusResponse setUserType(String type, Long user_id, Long admin_id, String password) {
        StatusResponse response = new StatusResponse();
        Optional<User> userOptional = usersRepository.findById(user_id);
        User admin = isSuperAdmin(admin_id, password);
        try {
            if (userOptional.isPresent() && admin != null) {
                User user = userOptional.get();
                user.setType(UserType.valueOf(type.toUpperCase(Locale.ROOT)));
            } else {
                response.setStatus("Пользователь не найден");
            }
        } catch (Exception e) {
            response.setStatus("Ошибка");
        }
        return response;
    }

    public StatusResponse setAchieveStatus(Long achieve_id, String status, Long admin_id, String password) {
        StatusResponse response = new StatusResponse();
        User admin = isAdmin(admin_id, password);
        Optional<Achieve> achieveOptional = achieveRepository.findById(achieve_id);
        if (achieveOptional.isPresent() && admin != null) {
            Achieve achieve = achieveOptional.get();
            achieve.setStatus(AchieveStatus.valueOf(status.toUpperCase(Locale.ROOT)));
        } else {
            response.setStatus("Отказано в доступе");
        }
        return response;
    }

    public StatusResponse deleteUser(Long user_id, Long admin_id, String password) {
        StatusResponse response = new StatusResponse();
        User admin = isSuperAdmin(admin_id, password);
        if (admin != null) {
            Optional<User> userOptional = usersRepository.findById(user_id);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                usersRepository.delete(user);
            } else {
                response.setStatus("Пользователь не найден");
            }
        } else {
            response.setStatus("Отказано в доступе");
        }
        return response;
    }

    private User isSuperAdmin(Long admin_id, String password) {
        Optional<User> adminOptional = usersRepository.findByIdAndPassword(admin_id, password);
        if (adminOptional.isPresent()) {
            if (adminOptional.get().getType().equals(UserType.SUPERADMIN)) {
                return adminOptional.get();
            }
        }
        return null;
    }

    private User isAdmin(Long admin_id, String password) {
        Optional<User> adminOptional = usersRepository.findByIdAndPassword(admin_id, password);
        if (adminOptional.isPresent()) {
            UserType type = adminOptional.get().getType();
            if (type.equals(UserType.SUPERADMIN) || type.equals(UserType.ADMIN)) {
                return adminOptional.get();
            }
        }
        return null;
    }
}