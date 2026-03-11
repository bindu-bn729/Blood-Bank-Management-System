package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    UserRepository userRepository;

    // show login page
    @GetMapping("/")
    public String home() {
        return "login";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    // login submit
    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password) {

        User user = userRepository.findByEmail(email);

        if(user != null && user.getPassword().equals(password)){
            return "redirect:/dashboard";
        }

        return "login";
    }

    // show register page
    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    // register submit
    @PostMapping("/register")
    public String register(@RequestParam String email,
                           @RequestParam String password) {

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        userRepository.save(user);

        return "redirect:/login";
    }
}