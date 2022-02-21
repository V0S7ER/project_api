package com.balabekyan.project_api.service;

import com.balabekyan.project_api.model.db.Achieve;
import com.balabekyan.project_api.model.db.User;
import com.balabekyan.project_api.model.db.enumerated.Achieve.AchieveLevel;
import com.balabekyan.project_api.model.db.enumerated.Achieve.AchievePlace;
import com.balabekyan.project_api.model.db.enumerated.Achieve.AchieveStatus;
import com.balabekyan.project_api.model.db.enumerated.Achieve.AchieveType;
import com.balabekyan.project_api.model.db.enumerated.UserType;
import com.balabekyan.project_api.model.dto.request.AchieveRequest;
import com.balabekyan.project_api.model.dto.response.StatusResponse;
import com.balabekyan.project_api.repository.AchieveRepository;
import com.balabekyan.project_api.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AchieveService {

    private final UsersRepository usersRepository;
    private final AchieveRepository achieveRepository;


    public Achieve get(long id) {
        return achieveRepository.getById(id);
    }

    public StatusResponse add(AchieveRequest request) {
        StatusResponse statusResponse = new StatusResponse();
        try {
            AchieveType type = AchieveType.valueOf(request.getType().toUpperCase(Locale.ROOT));
            AchieveLevel level = AchieveLevel.valueOf(request.getLevel().toUpperCase(Locale.ROOT));
            AchieveStatus status = AchieveStatus.valueOf(request.getStatus().toUpperCase(Locale.ROOT));
            AchievePlace place = AchievePlace.valueOf(request.getPlace().toUpperCase(Locale.ROOT));
            String description = request.getDescription();
            Optional<User> user = usersRepository.findById(request.getUser_id());
            int weight = WeightSystem.getWeight(type, level, place);
            if (user.isPresent()) {
                Achieve achieve = new Achieve(weight, type, level, status, place, description, user.get());
                achieveRepository.save(achieve);
            } else {
                statusResponse.setStatus("error");
            }
        } catch (Exception ex) {
            statusResponse.setStatus("error");
        }
        return statusResponse;
    }

    public StatusResponse delete(Long id, Long user_id, String password) {
        StatusResponse response = new StatusResponse();
        Optional<Achieve> achieve = achieveRepository.findById(id);
        if (achieve.isPresent()) {
            if (achieve.get().getUser().getId().equals(user_id) || isAdmin(user_id, password) != null) {
                achieveRepository.deleteById(id);
            }
        } else {
            response.setStatus("error");
        }
        return response;
    }

    public StatusResponse edit(Long id, AchieveRequest request) {
        StatusResponse statusResponse = new StatusResponse();
        Optional<Achieve> achieveOptional = achieveRepository.findById(id);
        try {
            if (achieveOptional.isPresent()) {
                if (isAdmin(request.getUser_id(), request.getUser_password()) != null || achieveOptional.get().getUser().getId().equals(request.getUser_id())) {
                    Achieve achieve = achieveOptional.get();
                    AchieveType type = request.getType() == null ? achieve.getType()
                            : AchieveType.valueOf(request.getType().toUpperCase(Locale.ROOT));
                    AchieveLevel level = request.getLevel() == null ? achieve.getLevel()
                            : AchieveLevel.valueOf(request.getLevel().toUpperCase(Locale.ROOT));
                    AchieveStatus status = request.getStatus() == null ? achieve.getStatus()
                            : AchieveStatus.valueOf(request.getStatus().toUpperCase(Locale.ROOT));
                    AchievePlace place = request.getPlace() == null ? achieve.getPlace()
                            : AchievePlace.valueOf(request.getPlace().toUpperCase(Locale.ROOT));
                    int weight = (request.getWeight() == 0) ? achieve.getWeight() : request.getWeight();
                    String description = request.getDescription() == null ? achieve.getDescription()
                            : request.getDescription();
                    achieve.setType(type);
                    achieve.setLevel(level);
                    achieve.setStatus(status);
                    achieve.setPlace(place);
                    achieve.setWeight(weight);
                    achieve.setDescription(description);
                    achieveRepository.save(achieve);
                } else {
                    statusResponse.setStatus("prohibited");
                }
            } else {
                statusResponse.setStatus("error");
            }
        } catch (Exception ex) {
            statusResponse.setStatus("error");
        }
        return statusResponse;
    }

    public List<Achieve> getNewAchieves() {
        return achieveRepository.findByStatusIs(AchieveStatus.NEW);
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
