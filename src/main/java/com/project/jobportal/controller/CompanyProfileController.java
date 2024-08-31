package com.project.jobportal.controller;

import com.project.jobportal.entity.CompanyProfile;
import com.project.jobportal.entity.RecruiterProfile;
import com.project.jobportal.entity.User;
import com.project.jobportal.repository.UserRepository;
import com.project.jobportal.service.CompanyProfileService;
import com.project.jobportal.service.RecruiterProfileService;
import com.project.jobportal.util.FileUploadUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.Optional;

@Controller()
@RequestMapping("/company-profile/")
public class CompanyProfileController {
    private final CompanyProfileService companyProfileService;
    private final UserRepository userRepository;
    private final RecruiterProfileService recruiterProfileService;

    @Autowired
    public CompanyProfileController(CompanyProfileService companyProfileService, UserRepository userRepository, RecruiterProfileService recruiterProfileService) {
        this.companyProfileService = companyProfileService;
        this.userRepository = userRepository;
        this.recruiterProfileService = recruiterProfileService;
    }

    /**
     * Handles the GET request for the company profile page.
     *
     * @param model The model to hold attributes for the view.
     * @return The name of the view to be rendered.
     */
    @GetMapping("/")
    public String companyProfile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            User user = userRepository.findByEmail(currentUserName).orElseThrow(() -> new RuntimeException("User not found"));
            Optional<RecruiterProfile> currentRecruiter = recruiterProfileService.getRecruiterProfileById(user.getUserId());
            if (currentRecruiter.isEmpty()) throw new RuntimeException("Recruiter not found");

            Optional<CompanyProfile> currentCompany = Optional.ofNullable(currentRecruiter.get().getCompany());
            currentCompany.ifPresentOrElse(company -> model.addAttribute("companyProfile", company), () -> model.addAttribute("companyProfile", new CompanyProfile()));
        }
        return "company_profile";
    }

    /**
     * Handles the POST request to add a new company profile.
     *
     * @param companyProfile The company profile to be added.
     * @param multipartFile  The image file associated with the company profile.
     * @param model          The model to hold attributes for the view.
     * @return The redirect URL to the dashboard.
     */
    @PostMapping("/addNew")
    public String addNew(@Valid CompanyProfile companyProfile, @RequestParam("image") MultipartFile multipartFile, Model model) {
        // Get the current authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        RecruiterProfile currentRecruiter = null;
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            User user = userRepository.findByEmail(currentUserName).orElseThrow(() -> new RuntimeException("User not found"));
            currentRecruiter = recruiterProfileService.getRecruiterProfileById(user.getUserId()).orElseThrow(() -> new RuntimeException("Recruiter not found"));
            CompanyProfile currenCompany = currentRecruiter.getCompany();
            if (currenCompany != null) companyProfile = currenCompany;
        }

        // Add the company profile to the model
        model.addAttribute("profile", companyProfile);

        // Process the uploaded image file
        String fileName = "";
        try {
            fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            companyProfile.setLogoUrl(fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        companyProfileService.addNew(companyProfile);
        currentRecruiter.setCompany(companyProfile);
        recruiterProfileService.addNew(currentRecruiter);

        // Save the uploaded image file to the specified directory
        String uploadDir = "photos/recruiter/" + companyProfile.getId();
        try {
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Redirect to the dashboard
        return "redirect:/dashboard/";
    }
}