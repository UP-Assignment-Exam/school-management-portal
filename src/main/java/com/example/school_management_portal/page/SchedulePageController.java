package com.example.school_management_portal.page;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/schedules")
public class SchedulePageController {
    /**
     * Display the schedules management page
     * URL: http://localhost:8080/schedules
     */
    @GetMapping
    public String schedulePage(Model model) {
        model.addAttribute("pageTitle", "Schedules Management");
        model.addAttribute("pageDescription", "Manage schedule records and information");
        return "schedules/schedule"; // This will look for templates/students/student.html
    }
}
