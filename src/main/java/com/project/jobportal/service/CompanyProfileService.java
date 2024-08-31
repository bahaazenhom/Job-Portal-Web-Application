package com.project.jobportal.service;

import com.project.jobportal.entity.CompanyProfile;
import com.project.jobportal.entity.RecruiterProfile;
import com.project.jobportal.repository.CompanyProfileRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompanyProfileService {
    private final CompanyProfileRepository companyProfileRepository;
    private final RecruiterProfileService recruiterProfileService;

    public CompanyProfileService(CompanyProfileRepository companyProfileRepository, RecruiterProfileService recruiterProfileService) {
        this.companyProfileRepository = companyProfileRepository;
        this.recruiterProfileService = recruiterProfileService;
    }

    public void addNew(CompanyProfile companyProfile){
        companyProfileRepository.save(companyProfile);
        RecruiterProfile recruiterProfile = new RecruiterProfile();
    }

    public Optional<CompanyProfile> getCompanyProfileById(int userId) {
        return companyProfileRepository.findById(userId);
    }
}
