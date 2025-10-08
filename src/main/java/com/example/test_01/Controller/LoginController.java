package com.example.test_01.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login01() {

        return "member/login";
    }

    @GetMapping("/admin_page")
    public String login02() {

        return "login/admin_page";
    }
}
