package com.project.jobportal.controller;

import com.project.jobportal.entity.*;
import com.project.jobportal.service.CompanyProfileService;
import com.project.jobportal.service.JobListingService;
import com.project.jobportal.service.RecruiterProfileService;
import com.project.jobportal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class JobPostActivityController {
    private final UserService userService;
    private final JobListingService jobListingService;
    private final CompanyProfileService companyProfileService;

    @Autowired
    public JobPostActivityController(UserService userService, JobListingService jobListingService, CompanyProfileService companyProfileService) {
        this.userService = userService;
        this.jobListingService = jobListingService;
        this.companyProfileService = companyProfileService;
    }

    @GetMapping("/dashboard/")
    public String searchJobs(Model model) {
        Object currentUserProfile = userService.getCurrentUserProfile();
        String currentUserName = userService.getCurrentUserName();
        model.addAttribute("username", currentUserName);
        model.addAttribute("user", currentUserProfile);
        if (currentUserProfile instanceof RecruiterProfile) {
            List<RecruiterJobsDto> recruiterJobs = jobListingService.getRecruiterJobs(((RecruiterProfile) currentUserProfile).getRecruiterId());
            model.addAttribute("jobList", recruiterJobs);
        }
        return "dashboard";
    }

    @GetMapping("/dashboard/add-job")
    public String addJob(Model model) {
        model.addAttribute("jobPostActivity", new JobListing());
        model.addAttribute("user", userService.getCurrentUserName());
        return "add-jobs";
    }

    @PostMapping("/dashboard/addNew")
    public String addJobPost(JobListing jobListing, Model model) {
        User currentUser = userService.getCurrentUser();
        if (currentUser != null) {
            jobListing.setPostedBy(currentUser);
        }
        jobListing.setPostedDate(new Date());
        jobListing.setCompany(companyProfileService.getCurrentCompany());
        model.addAttribute("jobPostActivity", jobListing);
        jobListingService.addJobListing(jobListing);
        return "redirect:/dashboard/";
    }

    @GetMapping("job-details-apply/{id}")
    public String jobDetailsApply(@PathVariable int id, Model model) {
        Optional<JobListing> jobListing = jobListingService.getJobListingById(id);
        jobListing.ifPresent(value -> model.addAttribute("jobDetails", value));
        model.addAttribute("user", userService.getCurrentUserProfile());
        return "job-details";
    }

    @PostMapping("job-details/edit/{id}")
    public String jobDetailsEdit(@PathVariable int id, Model model) {
        Optional<JobListing> jobListing = jobListingService.getJobListingById(id);
        jobListing.ifPresent(value -> model.addAttribute("jobPostActivity", value));
        model.addAttribute("user", userService.getCurrentUserProfile());
        return "add-jobs";
    }
}
