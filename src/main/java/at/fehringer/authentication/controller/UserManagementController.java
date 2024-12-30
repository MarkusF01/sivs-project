package at.fehringer.authentication.controller;

import at.fehringer.authentication.controller.dto.CreateUserRequest;
import at.fehringer.authentication.controller.dto.LoginRequest;
import at.fehringer.authentication.controller.dto.LoginResponse;
import at.fehringer.authentication.controller.dto.ResetPasswordRequest;
import at.fehringer.authentication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserManagementController {

    private final UserService userService;

    @Autowired
    public UserManagementController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/{username}/authorize")
    public ResponseEntity<?> login(@PathVariable String username, @RequestBody LoginRequest loginRequest) {
        String result = userService.authenticateUser(username, loginRequest);

        if (result == null) {
            return ResponseEntity.badRequest().body("Invalid credentials");
        }
        return ResponseEntity.ok(new LoginResponse(result));
    }

    @PostMapping
    public ResponseEntity<?> createAccount(@RequestBody CreateUserRequest createUserRequest) {
        boolean result = userService.insertUser(createUserRequest);

        if (!result) {
            return ResponseEntity.badRequest().body("Failed to create user");
        }
        return ResponseEntity.ok(new LoginResponse("/pages/diary?username=" + createUserRequest.getUsername()));
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
