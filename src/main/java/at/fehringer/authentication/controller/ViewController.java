package at.fehringer.authentication.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {
    @GetMapping("/")
    public String viewLogin() {
        return "index";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/pages/diary")
    public String viewDiary() {
        return "pages/diary";
    }

    @GetMapping("/pages/create_account")
    public String viewCreateAccount() {
        return "pages/create_account";
    }
}
