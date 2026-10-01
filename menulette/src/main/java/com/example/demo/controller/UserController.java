package com.example.demo.controller;

import com.example.demo.model.entity.UserEntity;
import com.example.demo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) { //コントローラーはサービスクラスと仲がいいからサービスクラスを定義

        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage() {

        return "login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String userId,
            Model model) {
        try {
            UserEntity user = userService.login(userId); //コントローラーから受け取った名前をサービスに渡して、その名前があるかDB(エンティティ)を探してきて！のコード

            model.addAttribute("userId", user.getUserId());
            return "calender";

        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "login";
        }
    }
}

