package at.fehringer.authentication.Controller.dto;

import lombok.Data;

@Data
public class CreateUserRequest {
    private String username;
    private String password;
    private String secretQuestion;
    private String secretAnswer;
}
