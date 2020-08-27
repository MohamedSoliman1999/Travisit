package com.travisit.travisitstandard.model.forms;

public class EditTravelerProfileForm extends EditProfileForm{
    String dateOfBirth;
    String nationality;

    public EditTravelerProfileForm(String fullName, String email, String phone, String dateOfBirth, String nationality) {
        super(fullName, email, phone);
        this.dateOfBirth = dateOfBirth;
        this.nationality = nationality;
    }
}
