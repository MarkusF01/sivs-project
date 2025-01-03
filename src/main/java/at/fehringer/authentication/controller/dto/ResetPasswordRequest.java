package at.fehringer.authentication.controller.dto;

import lombok.Data;

@Data
public class ResetPasswordRequest {
    private String password;
    private String secretAnswer;
}
