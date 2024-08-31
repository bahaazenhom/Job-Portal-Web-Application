package com.project.jobportal.controller;

import com.project.jobportal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class JobPostActivityController {
    private final UserService userService;

    @Autowired
    public JobPostActivityController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/dashboard/")
    public String searchJobs(Model model){
        System.out.println("From Dashboard");
        Object currentUserProfile = userService.getCurrentUserProfile();
        String currentUserName = userService.getCurrentUserName();
        System.out.println("current user ------------------"+currentUserName);
        model.addAttribute("username", currentUserName);
        model.addAttribute("user", currentUserProfile);
        return "dashboard";

    }
}
