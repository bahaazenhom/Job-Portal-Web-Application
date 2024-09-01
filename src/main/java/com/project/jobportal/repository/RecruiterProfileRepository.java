package com.project.jobportal.repository;

import com.project.jobportal.entity.RecruiterProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecruiterProfileRepository extends JpaRepository<RecruiterProfile, Integer> {
}
