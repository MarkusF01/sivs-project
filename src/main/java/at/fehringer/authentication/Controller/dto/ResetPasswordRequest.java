package at.fehringer.authentication.Controller.dto;

import lombok.Data;

@Data
public class ResetPasswordRequest {
    private String password;
    private String secretAnswer;
}
