package com.project.jobportal.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.util.Date;

@Entity
@Table(name = "job_listing")
public class JobListing {
    @Id
    @Column(name = "job_listing_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "posted_by_id", referencedColumnName = "user_id")
    private User postedBy;

    @Size(max = 255)
    @Column(name = "title")
    private String title;

    @Lob
    @Column(name = "description")
    private String description;

    @Size(max = 255)
    @Column(name = "job_type")
    private String jobType;

    @Column(name = "posted_date")
    private Date postedDate;

    @Size(max = 255)
    @Column(name = "remote_option")
    private String remoteOption;

    @Column(name = "salary")
    private Double salary;

    @Column(name = "salary_min")
    private Double salaryMin;

    @Column(name = "salary_max")
    private Double salaryMax;

    @Size(max = 10)
    @Column(name = "salary_currency", length = 10)
    private String salaryCurrency;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private CompanyProfile companyProfile;

    @Column(name = "years_of_experience")
    private Double yearsOfExperience;

    @Lob
    @Column(name = "benefits")
    private String benefits;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id", referencedColumnName = "location_id")
    private Location location;

    public JobListing() {
    }

    public JobListing(User postedBy, String title, String description, String jobType, Date postedDate, String remoteOption, Double salary, Double salaryMin, Double salaryMax, String salaryCurrency, CompanyProfile companyProfile, Double yearsOfExperience, String benefits, Location location) {
        this.postedBy = postedBy;
        this.title = title;
        this.description = description;
        this.jobType = jobType;
        this.postedDate = postedDate;
        this.remoteOption = remoteOption;
        this.salary = salary;
        this.salaryMin = salaryMin;
        this.salaryMax = salaryMax;
        this.salaryCurrency = salaryCurrency;
        this.companyProfile = companyProfile;
        this.yearsOfExperience = yearsOfExperience;
        this.benefits = benefits;
        this.location = location;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public Date getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(Date postedDate) {
        this.postedDate = postedDate;
    }

    public String getRemoteOption() {
        return remoteOption;
    }

    public void setRemoteOption(String remoteOption) {
        this.remoteOption = remoteOption;
    }

    public Double getSalaryMin() {
        return salaryMin;
    }

    public void setSalaryMin(Double salaryMin) {
        this.salaryMin = salaryMin;
    }

    public Double getSalaryMax() {
        return salaryMax;
    }

    public void setSalaryMax(Double salaryMax) {
        this.salaryMax = salaryMax;
    }

    public String getSalaryCurrency() {
        return salaryCurrency;
    }

    public void setSalaryCurrency(String salaryCurrency) {
        this.salaryCurrency = salaryCurrency;
    }

    public CompanyProfile getCompany() {
        return companyProfile;
    }

    public void setCompany(CompanyProfile companyProfile) {
        this.companyProfile = companyProfile;
    }

    public Double getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(Double yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public String getBenefits() {
        return benefits;
    }

    public void setBenefits(String benefits) {
        this.benefits = benefits;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public User getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(User postedBy) {
        this.postedBy = postedBy;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "JobListing{" +
                "id=" + id +
                ", postedBy=" + postedBy +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", jobType='" + jobType + '\'' +
                ", postedDate=" + postedDate +
                ", remoteOption='" + remoteOption + '\'' +
                ", salary=" + salary +
                ", salaryMin=" + salaryMin +
                ", salaryMax=" + salaryMax +
                ", salaryCurrency='" + salaryCurrency + '\'' +
                ", companyProfile=" + companyProfile +
                ", yearsOfExperience=" + yearsOfExperience +
                ", benefits='" + benefits + '\'' +
                ", location=" + location +
                '}';
    }
}