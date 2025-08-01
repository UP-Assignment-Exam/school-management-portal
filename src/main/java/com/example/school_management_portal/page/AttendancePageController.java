package com.example.school_management_portal.page;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/attendance")
public class AttendancePageController {
    /**
     * Display the attendances management page
     * URL: http://localhost:8080/attendance
     */
    @GetMapping
    public String attendancePage(HttpServletRequest request, Model model) {
        model.addAttribute("currentUri", request.getRequestURI());
        model.addAttribute("pageTitle", "Attendance Management");
        model.addAttribute("pageDescription", "Manage attendance records and information");
        return "attendances/attendance"; // This will look for templates/students/student.html
    }
}
