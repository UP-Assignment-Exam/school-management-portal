package com.example.school_management_portal.page;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthPageController {
    @GetMapping("/login")
    public String login(HttpServletRequest request, Model model) {
        model.addAttribute("currentUri", request.getRequestURI());
        model.addAttribute("pageTitle", "Login - School Management Portal");
        model.addAttribute("pageDescription", "Access your account on the School Management Portal.");
        return "login";
    }

    @GetMapping("/register")
    public String register(HttpServletRequest request, Model model) {
        model.addAttribute("currentUri", request.getRequestURI());
        model.addAttribute("pageTitle", "Register - School Management Portal");
        model.addAttribute("pageDescription", "Create a new account for the School Management Portal.");
        return "register";
    }

    @GetMapping("/")
    public String home(HttpServletRequest request, Model model) {
        model.addAttribute("currentUri", request.getRequestURI());

        model.addAttribute("pageTitle", "Home - School Management Portal");
        model.addAttribute("pageDescription", "Welcome to the School Management Portal.");
        return "redirect:/dashboard";
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpServletRequest request, Model model) {
        model.addAttribute("currentUri", request.getRequestURI());
        model.addAttribute("pageTitle", "Dashboard - School Management Portal");
        model.addAttribute("pageDescription", "Overview and management tools on your dashboard.");
        return "dashboard"; // You can create this template later
    }
}
