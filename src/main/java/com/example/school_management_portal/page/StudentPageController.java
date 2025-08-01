package com.example.school_management_portal.page;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/students")
public class StudentPageController {
    /**
     * Display the student management page
     * URL: http://localhost:8080/students
     */
    @GetMapping
    public String studentPage(HttpServletRequest request, Model model) {
        model.addAttribute("currentUri", request.getRequestURI());
        model.addAttribute("pageTitle", "Student Management");
        model.addAttribute("pageDescription", "Manage student records and information");
        return "students/student"; // This will look for templates/students/student.html
    }


//    /**
//     * Alternative route for student management
//     * URL: http://localhost:8080/students/manage
//     */
//    @GetMapping("/manage")
//    public String manageStudents(Model model) {
//        model.addAttribute("pageTitle", "Manage Students");
//        model.addAttribute("pageDescription", "Complete student management system");
//        return "students/student";
//    }
//
//    /**
//     * Student dashboard/overview page
//     * URL: http://localhost:8080/students/dashboard
//     */
//    @GetMapping("/dashboard")
//    public String studentDashboard(Model model) {
//        model.addAttribute("pageTitle", "Student Dashboard");
//        model.addAttribute("pageDescription", "Student overview and statistics");
//        return "students/student";
//    }
}
