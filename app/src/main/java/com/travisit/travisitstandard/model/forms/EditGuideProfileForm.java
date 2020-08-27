package com.travisit.travisitstandard.model.forms;

public class EditGuideProfileForm extends EditProfileForm{
    private String education = null; // Guide Specific
    private String workExperience = null; // Guide Specific
    private Integer hourlyRate = null; // Guide Specific
    private Integer membershipNumber = null; // Guide Specific
    private Integer licenseNumber = null;  // Guide Specific
    private String facebookLink = null; // Guide Specific
    private String twitterLink = null; // Guide Specific
    private String linkedinLink = null; // Guide Specific
    private String instagramLink = null; // Guide Specific

    public EditGuideProfileForm(String fullName, String email, String phone, String education, String workExperience, Integer hourlyRate, Integer membershipNumber, Integer licenseNumber) {
        super(fullName, email, phone);
        this.education = education;
        this.workExperience = workExperience;
        this.hourlyRate = hourlyRate;
        this.membershipNumber = membershipNumber;
        this.licenseNumber = licenseNumber;
    }
    public EditGuideProfileForm(String fullName, String email, String phone, String education, String workExperience, Integer hourlyRate, Integer membershipNumber, Integer licenseNumber, String facebookLink, String twitterLink, String linkedinLink, String instagramLink) {
        super(fullName, email, phone);
        this.education = education;
        this.workExperience = workExperience;
        this.hourlyRate = hourlyRate;
        this.membershipNumber = membershipNumber;
        this.licenseNumber = licenseNumber;
        this.facebookLink = facebookLink;
        this.twitterLink = twitterLink;
        this.linkedinLink = linkedinLink;
        this.instagramLink = instagramLink;
    }
}
