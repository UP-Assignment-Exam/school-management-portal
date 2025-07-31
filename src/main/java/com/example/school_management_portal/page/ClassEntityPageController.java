package com.example.school_management_portal.page;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/class")
public class ClassEntityPageController {
    /**
     * Display the class management page
     * URL: http://localhost:8080/class
     */
    @GetMapping
    public String classEntityPage(Model model) {
        model.addAttribute("pageTitle", "Class Management");
        model.addAttribute("pageDescription", "Manage class records and information");
        return "classEntitys/classEntity"; // This will look for templates/students/student.html
    }
}
