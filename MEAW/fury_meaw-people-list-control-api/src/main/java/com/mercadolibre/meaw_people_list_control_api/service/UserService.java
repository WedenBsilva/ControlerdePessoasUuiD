package com.mercadolibre.meaw_people_list_control_api.service;

import com.mercadolibre.meaw_people_list_control_api.models.User;
import com.mercadolibre.meaw_people_list_control_api.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> getUserById(UUID id) {
        return userRepository.findById(id);
    }

    public List<User> searchUsersByTerm(String termoBusca) {
        if (StringUtils.isNotBlank(termoBusca)) {
            return userRepository.findByNameContainingIgnoreCase(termoBusca);
        }
        return null;
    }

    public User createUser(User user) {
        if (isValidation(user)) {
            return userRepository.save(user);
        } else {
            return null;
        }
    }

    private boolean isValidation(User user) {
        return StringUtils.isNotBlank(user.getName())&&StringUtils.isNotBlank(user.getNickname())&&user.getBirthdate() != null&&!userRepository.existsByNickname(user.getNickname());
    }
}
