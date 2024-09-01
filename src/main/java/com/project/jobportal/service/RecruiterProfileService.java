package com.project.jobportal.service;

import com.project.jobportal.entity.RecruiterProfile;
import com.project.jobportal.repository.RecruiterProfileRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class RecruiterProfileService {
    private final RecruiterProfileRepository recruiterProfileRepository;

    @Autowired
    public RecruiterProfileService(RecruiterProfileRepository recruiterProfileRepository) {
        this.recruiterProfileRepository = recruiterProfileRepository;
    }

    public Optional<RecruiterProfile> getRecruiterProfileById(int id) {
        return recruiterProfileRepository.findById(id);
    }

    public void addNew(@Valid RecruiterProfile recruiterProfile) {
        recruiterProfileRepository.save(recruiterProfile);
    }

}
