package at.fehringer.authentication.controller;

import at.fehringer.authentication.controller.dto.CreateUserRequest;
import at.fehringer.authentication.controller.dto.LoginRequest;
import at.fehringer.authentication.controller.dto.LoginResponse;
import at.fehringer.authentication.controller.dto.ResetPasswordRequest;
import at.fehringer.authentication.service.TokenService;
import at.fehringer.authentication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserManagementController {

    private final UserService userService;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserManagementController(UserService userService, TokenService tokenService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(Authentication authentication) {
        String token = tokenService.generateToken(authentication.getName());

        return ResponseEntity.ok(new LoginResponse(token));
    }

    @PostMapping
    public ResponseEntity<?> createAccount(@RequestBody CreateUserRequest createUserRequest) {
        boolean result = userService.insertUser(createUserRequest);

        if (!result) {
            return ResponseEntity.badRequest().body("Failed to create user");
        }
        return ResponseEntity.ok().body("Successfully created user");
    }

    @PostMapping("/{username}/reset-password")
    public ResponseEntity<?> resetPassword(@PathVariable String username, @RequestBody ResetPasswordRequest resetPasswordRequest) {
        boolean result = userService.resetPassword(username, resetPasswordRequest);
        if (!result) {
            return ResponseEntity.badRequest().body("Failed to reset password");
        }
        return ResponseEntity.ok("Password reset successfully");
    }

    @GetMapping("/{username}/reset-password")
    public ResponseEntity<?> initiateResetPassword(@RequestParam String username) {
        String secretQuestion = userService.getSecretQuestion(username);
        if (secretQuestion == null) {
            return ResponseEntity.badRequest().body("User not found");
        }
        return ResponseEntity.ok(secretQuestion);
    }
}
