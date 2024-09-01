package com.project.jobportal.controller;

import com.project.jobportal.repository.JobListingRepository;
import com.project.jobportal.service.JobListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.project.jobportal.entity.User;

@Controller
public class HomeController {
    private final JobListingService jobListingService;

    @Autowired
    public HomeController(JobListingService jobListingService) {
        this.jobListingService = jobListingService;
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

}
