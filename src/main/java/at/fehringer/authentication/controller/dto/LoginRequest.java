package at.fehringer.authentication.controller.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
