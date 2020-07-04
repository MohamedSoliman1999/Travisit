package com.travisit.travisitstandard.model;

import com.google.gson.annotations.SerializedName;
import com.travisit.travisitstandard.utils.UserType;

import java.io.Serializable;

public class User implements Serializable {
    private String token = null;
    private Integer id = null;
    private String fullName = null;
    private String email = null;
    private Boolean isActive = false;
    private String phone = null;
    private String approvementStatus = null; // Guide Specific
    private String education = null; // Guide Specific
    private String workExperience = null; // Guide Specific
    private Integer hourlyRate = null; // Guide Specific
    private Integer membershipNumber = null; // Guide Specific
    private Integer licenseNumber = null;  // Guide Specific
    private Integer balance = null; // Guide Specific
    private String facebookLink = null; // Guide Specific
    private String twitterLink = null; // Guide Specific
    private String linkedinLink = null; // Guide Specific
    private String instagramLink = null; // Guide Specific
    private String dateOfBirth = null; // Traveler Specific
    private String nationality = null; // Traveler Specific
    private String type = null;
    private String profilePicture = null;

    // Guide Constructor
    public User(String token, Integer id, String fullName, String email, Boolean isActive, String phone, String approvementStatus, String education, String workExperience, Integer hourlyRate, Integer membershipNumber, Integer licenseNumber, Integer balance, String facebookLink, String twitterLink, String linkedinLink, String instagramLink, String profilePicture) {
        this.token = token;
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.isActive = isActive;
        this.phone = phone;
        this.approvementStatus = approvementStatus;
        this.education = education;
        this.workExperience = workExperience;
        this.hourlyRate = hourlyRate;
        this.membershipNumber = membershipNumber;
        this.licenseNumber = licenseNumber;
        this.balance = balance;
        this.facebookLink = facebookLink;
        this.twitterLink = twitterLink;
        this.linkedinLink = linkedinLink;
        this.instagramLink = instagramLink;
        this.type = "guide";
        this.profilePicture = profilePicture;
    }

    //Traveler Constructor
    public User(Integer id, String fullName, String email, Boolean isActive, String phone, String dateOfBirth, String nationality, String profilePicture) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.isActive = isActive;
        this.phone = phone;
        this.dateOfBirth = dateOfBirth;
        this.nationality = nationality;
        this.type = "traveler";
        this.profilePicture = profilePicture;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getApprovementStatus() {
        return approvementStatus;
    }

    public void setApprovementStatus(String approvementStatus) {
        this.approvementStatus = approvementStatus;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getWorkExperience() {
        return workExperience;
    }

    public void setWorkExperience(String workExperience) {
        this.workExperience = workExperience;
    }

    public Integer getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(Integer hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public Integer getMembershipNumber() {
        return membershipNumber;
    }

    public void setMembershipNumber(Integer membershipNumber) {
        this.membershipNumber = membershipNumber;
    }

    public Integer getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(Integer licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public String getFacebookLink() {
        return facebookLink;
    }

    public void setFacebookLink(String facebookLink) {
        this.facebookLink = facebookLink;
    }

    public String getTwitterLink() {
        return twitterLink;
    }

    public void setTwitterLink(String twitterLink) {
        this.twitterLink = twitterLink;
    }

    public String getLinkedinLink() {
        return linkedinLink;
    }

    public void setLinkedinLink(String linkedinLink) {
        this.linkedinLink = linkedinLink;
    }

    public String getInstagramLink() {
        return instagramLink;
    }

    public void setInstagramLink(String instagramLink) {
        this.instagramLink = instagramLink;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }
}
