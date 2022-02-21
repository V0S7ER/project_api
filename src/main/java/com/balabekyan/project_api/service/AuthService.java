package com.balabekyan.project_api.service;

import com.balabekyan.project_api.model.db.User;
import com.balabekyan.project_api.model.db.enumerated.UserType;
import com.balabekyan.project_api.model.dto.request.LoginRequest;
import com.balabekyan.project_api.model.dto.request.RegisterRequest;
import com.balabekyan.project_api.model.dto.response.StatusResponse;
import com.balabekyan.project_api.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UsersRepository usersRepository;

    private final Map<Long, User> currentSessions = new HashMap<>();

    public StatusResponse register(RegisterRequest request) {
        StatusResponse response = new StatusResponse();
        String password = request.getPassword().trim();
        String email = request.getEmail().trim();
        String name = request.getName().trim();
        String surname = request.getSurname().trim();

        if (!emailIsCorrect(email)) {
            response.setStatus("Некорректный email");
        } else if (!passwordIsCorrect(password)) {
            response.setStatus("Короткий пароль");
        } else if (!nameIsCorrect(name)) {
            response.setStatus("Некорректное имя");
//        } else if (!nameIsCorrect(surname)) {
//            response.setStatus("Некорректная фамилия");
//        } else if (emailExists(email)) {
//            response.setStatus("Email уже зарегистрирован");
        } else {
            User user = new User(email, password, name, surname);
            user.setType(UserType.DEFAULT);
            usersRepository.save(user);
        }
        return response;
    }

    public User login(LoginRequest request) {
        String email = request.getEmail();
        String password = request.getPassword();
        Optional<User> userOptional;
        if (emailIsCorrect(email) && passwordIsCorrect(password)) {
            userOptional = usersRepository.findByEmailAndPassword(email, password);
            if (userOptional.isPresent()) {
                currentSessions.put(userOptional.get().getId(), userOptional.get());
                return userOptional.get();
            }
        }
        return null;
    }

    private boolean emailExists(String email) {
        return usersRepository.countByEmail(email) > 0;
    }

    private boolean emailIsCorrect(String email) {
        return email.matches("\\p{all}+@[a-z]+\\.[a-z]{1,3}");
    }

    private boolean nameIsCorrect(String name) {
        return name.matches("[А-Я][а-я]{1,15}");
    }

    private boolean passwordIsCorrect(String password) {
        return password.length() >= 6;
    }
}