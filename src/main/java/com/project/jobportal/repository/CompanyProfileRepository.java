package com.project.jobportal.repository;

import com.project.jobportal.entity.CompanyProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyProfileRepository extends JpaRepository<CompanyProfile, Integer> {
}
