package com.project.jobportal.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "recruiter_profile")
public class RecruiterProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recruiter_id", nullable = false)
    private int recruiterId;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "recruiter_id", nullable = false)
    private User user;

    @Size(max = 255)
    private String firstName;

    @Size(max = 255)
    private String lastName;

    @Size(max = 64)
    @Column(name = "profile_photo_url", length = 64)
    private String profilePhotoUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private CompanyProfile companyProfile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location location;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public CompanyProfile getCompany() {
        return companyProfile;
    }

    public void setCompany(CompanyProfile companyProfile) {
        this.companyProfile = companyProfile;
    }

    public RecruiterProfile() {
    }

    public RecruiterProfile(int recruiterId, User user, String firstName, String lastName, String profilePhotoUrl) {
        this.recruiterId = recruiterId;
        this.user = user;
        this.firstName = firstName;
        this.lastName = lastName;
        this.profilePhotoUrl = profilePhotoUrl;
    }

    public int getRecruiterId() {
        return recruiterId;
    }

    public void setRecruiterId(int recruiterId) {
        this.recruiterId = recruiterId;
    }

    @Transient
    public String getProfilePhoto() {
        return profilePhotoUrl == null ? null : "/photos/recruiter/" + recruiterId + "/" + profilePhotoUrl;
    }

    public void setProfilePhotoUrl(String profilePhotoUrl) {
        this.profilePhotoUrl = profilePhotoUrl;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "RecruiterProfile{" +
                "recruiterId=" + recruiterId +
                ", user=" + user +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", profilePhotoUrl='" + profilePhotoUrl + '\'' +
                ", companyProfile=" + companyProfile +
                ", location=" + location +
                '}';
    }
}
