package com.project.jobportal.controller;

import ch.qos.logback.core.util.StringUtil;
import com.project.jobportal.entity.RecruiterProfile;
import com.project.jobportal.entity.User;
import com.project.jobportal.repository.UserRepository;
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

@Controller
@RequestMapping("/recruiter-profile/")
public class RecruiterProfileController {
    private final UserRepository userRepository;
    private final RecruiterProfileService recruiterProfileService;

    @Autowired
    public RecruiterProfileController(UserRepository userRepository, RecruiterProfileService recruiterProfileService) {
        this.userRepository = userRepository;
        this.recruiterProfileService = recruiterProfileService;
    }


    @GetMapping("/")
    public String recruiterProfile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            User user = userRepository.findByEmail(currentUserName).orElseThrow(() -> new RuntimeException("User not found"));
            Optional<RecruiterProfile> currentRecruiterProfile = recruiterProfileService.getRecruiterProfileById(user.getUserId());
            currentRecruiterProfile.ifPresent(recruiterProfile -> model.addAttribute("profile", recruiterProfile));
        }

        return "recruiter_profile";
    }

    /**
     * Handles the addition of a new recruiter profile.
     *
     * @param recruiterProfile The recruiter profile to be added.
     * @param multipartFile    The image file associated with the recruiter profile.
     * @param model            The model to hold attributes for the view.
     * @return The redirect URL to the dashboard.
     */
    @PostMapping("/addNew")
    public String addNew(@Valid RecruiterProfile recruiterProfile,
                         @RequestParam("image") MultipartFile multipartFile,
                         Model model) {
        // Get the current authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            User user = userRepository.
                    findByEmail(currentUserName).orElseThrow(() -> new RuntimeException("User not found"));
            recruiterProfile = recruiterProfileService.getRecruiterProfileById(user.getUserId()).orElse(null);
        }

        // Add the recruiter profile to the model
        model.addAttribute("profile", recruiterProfile);

        // Process the uploaded image file
        String fileName = "";
        try {
            fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            recruiterProfile.setProfilePhotoUrl(fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Save the recruiter profile
        recruiterProfileService.addNew(recruiterProfile);

        // Save the uploaded image file to the specified directory
        String uploadDir = "photos/recruiter/" + recruiterProfile.getRecruiterId();
        try {
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Redirect to the dashboard
        return "redirect:/dashboard/";
    }
}
