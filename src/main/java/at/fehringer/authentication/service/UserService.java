package at.fehringer.authentication.service;

import at.fehringer.authentication.controller.dto.CreateUserRequest;
import at.fehringer.authentication.controller.dto.LoginRequest;
import at.fehringer.authentication.controller.dto.ResetPasswordRequest;
import at.fehringer.authentication.repository.UserRepository;
import at.fehringer.authentication.repository.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String authenticateUser(String username, LoginRequest loginRequest) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user != null && user.getPassword().equals(loginRequest.getPassword())) {
            return "/pages/diary?username=" + username;
        }
        return null;
    }

    public boolean insertUser(CreateUserRequest createUserRequest) {
        if (userRepository.existsByUsername(createUserRequest.getUsername())) {
            return false;
        }

        User user = new User();
        user.setUsername(createUserRequest.getUsername());
        user.setPassword(createUserRequest.getPassword());
        user.setSecretQuestion(createUserRequest.getSecretQuestion());
        user.setSecretAnswer(createUserRequest.getSecretAnswer());

        userRepository.save(user);
        return true;
    }

    public boolean resetPassword(String username, ResetPasswordRequest resetPasswordRequest) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user != null && user.getSecretAnswer().equals(resetPasswordRequest.getSecretAnswer())) {
            user.setPassword(resetPasswordRequest.getPassword());
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public String getSecretQuestion(String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        return user != null ? user.getSecretQuestion() : null;
    }
}
