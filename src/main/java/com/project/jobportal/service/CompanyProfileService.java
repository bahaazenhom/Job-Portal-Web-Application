package com.project.jobportal.service;

import com.project.jobportal.entity.CompanyProfile;
import com.project.jobportal.entity.RecruiterProfile;
import com.project.jobportal.entity.User;
import com.project.jobportal.repository.CompanyProfileRepository;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompanyProfileService {
    private final CompanyProfileRepository companyProfileRepository;
    private final RecruiterProfileService recruiterProfileService;
    private final UserService userService;

    public CompanyProfileService(CompanyProfileRepository companyProfileRepository, RecruiterProfileService recruiterProfileService, UserService userService) {
        this.companyProfileRepository = companyProfileRepository;
        this.recruiterProfileService = recruiterProfileService;
        this.userService = userService;
    }

    public void addNew(CompanyProfile companyProfile){
        companyProfileRepository.save(companyProfile);
        RecruiterProfile recruiterProfile = new RecruiterProfile();
    }

    public Optional<CompanyProfile> getCompanyProfileById(int userId) {
        return companyProfileRepository.findById(userId);
    }

    public CompanyProfile getCurrentCompany() {
        User currentUser = userService.getCurrentUser();
        if(currentUser == null)return null;

        RecruiterProfile currentRecruiterProfile =
                recruiterProfileService.getRecruiterProfileById(currentUser.getUserId()).orElse(null);

        return currentRecruiterProfile == null ? null : currentRecruiterProfile.getCompany();
    }
}
