package com.project.jobportal.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "company")
public class CompanyProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @Column(name = "name")
    private String name;

    @Size(max = 255)
    @Column(name = "logo_url")
    private String logoUrl;

    @Size(max = 255)
    @Column(name = "website_url")
    private String websiteUrl;

    @Lob
    @Column(name = "description")
    private String description;

    @Size(max = 255)
    @Column(name = "company_size")
    private String companySize;

    @Lob
    @Column(name = "why_join_us")
    private String whyJoinUs;

    @Column(name = "founded_date")
    private LocalDate foundedDate;

    @ManyToMany
    @JoinTable(name = "company_location",
            joinColumns = @JoinColumn(name = "company_id"),
            inverseJoinColumns = @JoinColumn(name = "location_id"))
    private Set<Location> locations = new LinkedHashSet<>();

    @OneToMany(mappedBy = "companyProfile")
    private Set<JobListing> jobListings = new LinkedHashSet<>();

    @OneToMany(mappedBy = "companyProfile")
    private Set<RecruiterProfile> recruiterProfiles = new LinkedHashSet<>();

    public CompanyProfile(int companyId, String name, String s) {
        this.id = companyId;
        this.name = name;
        this.logoUrl = s;
    }

    public CompanyProfile() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCompanySize() {
        return companySize;
    }

    public void setCompanySize(String companySize) {
        this.companySize = companySize;
    }

    public String getWhyJoinUs() {
        return whyJoinUs;
    }

    public void setWhyJoinUs(String whyJoinUs) {
        this.whyJoinUs = whyJoinUs;
    }

    public LocalDate getFoundedDate() {
        return foundedDate;
    }

    public void setFoundedDate(LocalDate foundedDate) {
        this.foundedDate = foundedDate;
    }

    public Set<Location> getLocations() {
        return locations;
    }

    public void setLocations(Set<Location> locations) {
        this.locations = locations;
    }

    public Set<JobListing> getJobListings() {
        return jobListings;
    }

    public void setJobListings(Set<JobListing> jobListings) {
        this.jobListings = jobListings;
    }

    public Set<RecruiterProfile> getRecruiterProfiles() {
        return recruiterProfiles;
    }

    public void setRecruiterProfiles(Set<RecruiterProfile> recruiterProfiles) {
        this.recruiterProfiles = recruiterProfiles;
    }

    @Override
    public String toString() {
        return "CompanyProfile{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", logoUrl='" + logoUrl + '\'' +
                ", websiteUrl='" + websiteUrl + '\'' +
                ", description='" + description + '\'' +
                ", companySize='" + companySize + '\'' +
                ", whyJoinUs='" + whyJoinUs + '\'' +
                ", foundedDate=" + foundedDate +
                ", locations=" + locations +
                ", recruiterProfiles=" + recruiterProfiles +
                '}';
    }
}