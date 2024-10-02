package com.project.jobportal.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "job_seeker_profile")
public class JobSeekerProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_seeker_id", nullable = false)
    private Integer id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "job_seeker_id", nullable = false)
    private User user;

    @Size(max = 255)
    private String firstName;

    @Size(max = 255)
    private String lastName;

    @Size(max = 255)
    @Column(name = "profile_photo_url")
    private String profilePhotoUrl;

    @Size(max = 255)
    @Column(name = "resume_url")
    private String resumeUrl;

    @Size(max = 255)
    @Column(name = "employment_type")
    private String employmentType;

    @Size(max = 255)
    @Column(name = "work_authorization")
    private String workAuthorization;

    @Size(max = 10)
    @Column(name = "gender", length = 10)
    private String gender;

    @OneToMany(targetEntity = Skill.class, mappedBy = "jobSeeker", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Skill> skills;

    public JobSeekerProfile() {
    }

    public JobSeekerProfile(User user, String firstName, String lastName, String profilePhotoUrl, String resumeUrl, String employmentType, String workAuthorization, String gender, List<Skill> skills) {
        this.user = user;
        this.firstName = firstName;
        this.lastName = lastName;
        this.profilePhotoUrl = profilePhotoUrl;
        this.resumeUrl = resumeUrl;
        this.employmentType = employmentType;
        this.workAuthorization = workAuthorization;
        this.gender = gender;
        this.skills = skills;
    }
    @Transient
    public String getPhotoImagePath(){
        if(profilePhotoUrl == null || id == null) return null;
        return "/photos/JobSeeker/ProfilePhotos/" + id + "/" + profilePhotoUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getProfilePhoto() {
        return profilePhotoUrl;
    }

    public void setProfilePhotoUrl(String profilePhotoUrl) {
        this.profilePhotoUrl = profilePhotoUrl;
    }

    public String getResumeUrl() {
        return resumeUrl;
    }

    public void setResumeUrl(String resumeUrl) {
        this.resumeUrl = resumeUrl;
    }

    public String getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(String employmentType) {
        this.employmentType = employmentType;
    }

    public String getWorkAuthorization() {
        return workAuthorization;
    }

    public void setWorkAuthorization(String workAuthorization) {
        this.workAuthorization = workAuthorization;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    @Override
    public String toString() {
        return "JobSeekerProfile{" +
                "id=" + id +
                ", user=" + user +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", profilePhotoUrl='" + profilePhotoUrl + '\'' +
                ", resumeUrl='" + resumeUrl + '\'' +
                ", employmentType='" + employmentType + '\'' +
                ", workAuthorization='" + workAuthorization + '\'' +
                ", gender='" + gender + '\'' +
                ", skills=" + skills +
                '}';
    }
}