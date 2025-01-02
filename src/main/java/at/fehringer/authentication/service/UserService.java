package at.fehringer.authentication.service;

import at.fehringer.authentication.controller.dto.CreateUserRequest;
import at.fehringer.authentication.controller.dto.ResetPasswordRequest;
import at.fehringer.authentication.repository.UserRepository;
import at.fehringer.authentication.repository.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean insertUser(CreateUserRequest createUserRequest) {
        if (userRepository.existsByUsername(createUserRequest.getUsername())) {
            return false;
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        User user = new User();
        user.setUsername(createUserRequest.getUsername());
        user.setPassword(encoder.encode(createUserRequest.getPassword()));
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> result = userRepository.findByUsername(username);

        if (result.isEmpty()) {
            throw new UsernameNotFoundException("User with username: " + username + " not found");
        }

        return org.springframework.security.core.userdetails.User.builder()
                .username(result.get().getUsername())
                .password(result.get().getPassword())
                .roles("USER")
                .build();
    }
}
