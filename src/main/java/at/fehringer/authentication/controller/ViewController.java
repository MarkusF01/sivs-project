package at.fehringer.authentication.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {
    @GetMapping("/")
    public String viewLogin() {
        return "index";
    }

    @GetMapping("/pages/diary")
    public String viewDiary() {
        return "pages/diary";
    }

    @GetMapping("/pages/create_account")
    public String viewCreateAccount() {
        return "pages/create_account";
    }
}
