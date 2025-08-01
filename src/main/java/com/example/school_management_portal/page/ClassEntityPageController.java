package com.example.school_management_portal.page;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/classes")
public class ClassEntityPageController {
    /**
     * Display the class management page
     * URL: http://localhost:8080/classes
     */
    @GetMapping
    public String classEntityPage(HttpServletRequest request, Model model) {
        model.addAttribute("currentUri", request.getRequestURI());
        model.addAttribute("pageTitle", "Class Management");
        model.addAttribute("pageDescription", "Manage class records and information");
        return "classEntitys/classEntity"; // This will look for templates/students/student.html
    }
}
