package com.example.school_management_portal.page;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/teachers")
public class TeacherPageController {
    /**
     * Display the teacher management page
     * URL: http://localhost:8080/teacher
     */
    @GetMapping
    public String teacherPage(HttpServletRequest request, Model model) {
        model.addAttribute("currentUri", request.getRequestURI());
        model.addAttribute("pageTitle", "Teacher Management");
        model.addAttribute("pageDescription", "Manage teacher records and information");
        return "teachers/teacher"; // This will look for templates/students/student.html
    }
}
