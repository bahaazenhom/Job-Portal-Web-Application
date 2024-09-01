package com.project.jobportal.service;

import com.project.jobportal.entity.JobSeekerProfile;
import com.project.jobportal.entity.RecruiterProfile;
import com.project.jobportal.entity.User;
import com.project.jobportal.repository.JobSeekerProfileRepository;
import com.project.jobportal.repository.RecruiterProfileRepository;
import com.project.jobportal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RecruiterProfileRepository recruiterProfileRepository;
    private final JobSeekerProfileRepository jobSeekerProfileRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RecruiterProfileRepository recruiterProfileRepository, JobSeekerProfileRepository jobSeekerProfileRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.recruiterProfileRepository = recruiterProfileRepository;
        this.jobSeekerProfileRepository = jobSeekerProfileRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void addNew(User user) {
        user.setActive(true);
        user.setRegistrationDate(new Date(System.currentTimeMillis()));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        int userType = user.getUserTypeId().getUserTypeId();
        userRepository.save(user);
        if (userType == 1) {
            JobSeekerProfile jobSeekerProfile = new JobSeekerProfile();
            jobSeekerProfile.setUser(user);
            jobSeekerProfileRepository.save(jobSeekerProfile);
        } else if (userType == 2) {
            System.out.println("Recruiter Profile");
            RecruiterProfile recruiterProfile = new RecruiterProfile();
            recruiterProfile.setUser(user);
            recruiterProfileRepository.save(recruiterProfile);
        }

    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Object getCurrentUserProfile() {
        String userName = getCurrentUserName();
        User user = userRepository.findByEmail(userName).orElseThrow(() -> new UsernameNotFoundException("Could not found " + "user"));
        int userType = user.getUserTypeId().getUserTypeId();
        if (userType == 1) {
            return jobSeekerProfileRepository.findById(user.getUserId())
                    .orElse(new JobSeekerProfile());
        } else if (userType == 2) {
            return recruiterProfileRepository.findById(user.getUserId())
                    .orElse(new RecruiterProfile());
        }


        return null;
    }

    public String getCurrentUserName() {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return authentication.getName();
        }
        return null;
    }


    public User getCurrentUser() {
        String userName = getCurrentUserName();
        return userRepository.findByEmail(userName)
                .orElseThrow(() -> new UsernameNotFoundException("Could not found " + "user"));
    }
}
